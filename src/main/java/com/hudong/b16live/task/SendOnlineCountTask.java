package com.hudong.b16live.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hudong.b16live.bean.TLiveInfo;
import com.hudong.b16live.service.RedisService;
import com.hudong.b16live.service.TLiveImService;
import com.hudong.b16live.service.TLiveInfoService;
import com.hudong.b16live.utils.DateUtils;
import com.hudong.b16live.utils.DistributedLockUtil;
import com.hudong.b16live.utils.PageBean;
import com.hudong.b16live.utils.RedisKeyConstant;
import com.hudong.b16live.utils.lock.DistributedLock;
import com.hudong.core.common.lang.utils.DigestUtils;
import com.hudong.core.common.logger.HuDongErrorCodeEnum;
import com.hudong.core.common.logger.HuDongLogger;
import com.hudong.core.common.logger.HuDongLoggerFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author yimi099
 * @Title: SendOnlineCountTask
 * @Copyright: Copyright (c) 2005
 * @Description:定时发送在线人数
 * @Company: 在线
 * @Created on 2018/8/7 12:22
 */

@Component
@EnableScheduling
public class SendOnlineCountTask {
    private final static HuDongLogger logger = HuDongLoggerFactory.getLogger(SendOnlineCountTask.class);

    // 过期时间6个小时
    protected static final long EXPRIE_TIME = 60 * 60 * 6;
    @Autowired
    private TLiveImService tLiveImService;
    @Autowired
    private TLiveInfoService tLiveInfoService;
    @Autowired
    private RedisService redisService;


    @Scheduled(cron = "0 0/2 * * * *")
    private void sendOnlineCount() {
        String  key = DigestUtils.digest(RedisKeyConstant.SYNC_ONLINE_COUNT_LOCK_KEY);
        DistributedLock lock = DistributedLockUtil.getDistributedLock(key);
        try {
            if (lock.acquire()) {
                    logger.info("已获取到锁" );
                    int pageNo = 1;
                    int pageSize = 200;
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("status", "1");
                    List<TLiveInfo> dataList = new ArrayList<TLiveInfo>();
                    //直播中的
                    PageBean<TLiveInfo> list = tLiveInfoService.getAdminListPageV2(map, pageSize, pageNo);
                    if (list != null && list.getItems() != null) {
                        dataList.addAll( list.getItems());
                    }
                    if(dataList.size()<200){
                        //未直播的
                        PageBean<TLiveInfo> listNew = tLiveInfoService.getAdminListPageV2(map, pageSize, pageNo);
                        if (listNew != null && listNew.getItems() != null) {
                            dataList.addAll(listNew.getItems());
                        }

                    }
                    Map countMap= (Map) redisService.get(RedisKeyConstant.ONLINE_COUNT_KEY);
                    if(countMap==null){
                        countMap=new HashMap();
                    }
                    for (TLiveInfo data : dataList) {
                        Date now = new Date();
                        if (StringUtils.isNotBlank(data.getDmGroupId()) && data.getStartTime() != null) {
                            Object obj = redisService.get(DigestUtils.digest(data.getDmGroupId()));
                            if (obj == null || (long) obj < (System.currentTimeMillis() - 2 * 60 * 1000)) {
                                int memberNumber = tLiveImService.getMemberNumber(data.getDmGroupId());
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("live_av_id", data.getDmGroupId());
                                jsonObject.put("live_start_time", data.getStartTime());
                                jsonObject.put("live_end_time", data.getEndTime());
                                jsonObject.put("current_time", DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                                memberNumber = memberNumber * Integer.parseInt(data.getOnlineNumZsxs()) + GetRandomNum(1, 9);
                                countMap.put(data.getId(),memberNumber);
                                jsonObject.put("member_number", memberNumber);
                                jsonObject.put("msg_type", "1");//在线人数
                                if (tLiveImService.sendMessage(data.getDmGroupId(), jsonObject.toJSONString())) {
                                    redisService.set(DigestUtils.digest(data.getDmGroupId()), System.currentTimeMillis(), EXPRIE_TIME);
                                }
                            }
                        }
                    }
                    redisService.set(RedisKeyConstant.ONLINE_COUNT_KEY,countMap);
              } else {
                    logger.info("没有获取到锁，不执行任务!");
                    return;
              }
        }finally {
            if (lock != null) {
                lock.release();
                logger.info("任务结束，释放锁!");
            }else{
                logger.info("没有获取到锁，无需释放锁!");
            }
        }

    }


    /**
     * 获取随机数
     *
     * @param min
     * @param max
     * @return
     */
    public static int GetRandomNum(int min, int max) {
        int range = max - min;
        double rand = Math.random();
        return (int) (min + Math.round(rand * range));
    }
}
