package com.hudong.b16live.task;
import com.hudong.b16live.bean.TLiveInfo;
import com.hudong.b16live.service.PushMessageService;
import com.hudong.b16live.service.TLiveInfoService;
import com.hudong.b16live.utils.DistributedLockUtil;
import com.hudong.b16live.utils.RedisKeyConstant;
import com.hudong.b16live.utils.lock.DistributedLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;


/**
 * 定时任务推送视频播放通知
 */
@Component
@EnableScheduling
public class SendMessageTask {
    private static Logger logger = LoggerFactory.getLogger( SendMessageTask.class );

    @Autowired
    private TLiveInfoService tLiveInfoService;
    @Autowired
    private PushMessageService pushMessageService;



    @Scheduled(cron = "0 0/15 * * * *")
    private void sendMessageRun() {
        DistributedLock lock = DistributedLockUtil.getDistributedLock(RedisKeyConstant.PUSH_MSG_LOCK_KEY);
        try {
            if (lock.acquire()) {
                List<TLiveInfo> liveList=tLiveInfoService.findStartLiveList();
                for(TLiveInfo ti:liveList){
                    pushMessageService.sendMessageToSubscribeUser(ti);
                }
            }
        }finally {
                if (lock != null) {
                    lock.release();
                }
         }

    }

}

