package com.hudong.b16live.service;

import com.github.pagehelper.PageHelper;
import com.hudong.b16live.bean.TLiveInfo;
import com.hudong.b16live.mapper.TLiveInfoMapper;
import com.hudong.b16live.utils.DistributedLockUtil;
import com.hudong.b16live.utils.PageBean;
import com.hudong.b16live.utils.lock.DistributedLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;


/**
 * dao实现层
 * @author Lenovo
 * 2018-08-03 11:08:10
 */
@Service
public class TLiveInfoService {

	private static final Logger logger = Logger.getLogger(TLiveInfoService.class);
	private  String preyKey="b16live.";
	private  String hotKey="b16live.hotLive.id";//存放热点数据:首页直播
	private  String hotListKey="b16live.hotLive.list";//存放热点数据:直播列表
	private  String hotListTotalKey="b16live.hotLive.list.total";//直播总数



	@Autowired
	private TLiveInfoMapper tLiveInfoMapper;

	@Autowired
	RedisService redisService;



	/**
	 * 设置加载更多
	 * @param pageNo
	 */
	public void setHotListKey(Integer pageNo){
		if(pageNo!=null){
			Map<String,String>map=new HashMap<String,String>();//暂时参数为空
			PageBean<TLiveInfo> pageData=this.getAppLiveList(map,pageNo,10);
			redisService.set(hotListKey,pageData.getItems());
			redisService.set(hotListTotalKey,pageData.getTotalNum());
		}
	}

	/**
	 * 获取直播更多的列表
	 * @param pageNo
	 * @return
	 */
	public List<TLiveInfo> getHotListKey(Integer pageNo){
		List<TLiveInfo> list=null;
		list=(List<TLiveInfo>)redisService.get(hotListKey);
		if(list==null){
			Map<String,String>map=new HashMap<String,String>();//暂时参数为空
			PageBean<TLiveInfo> pageData=this.getAppLiveList(map,pageNo,10);
			redisService.set(hotListKey,pageData.getItems());
			redisService.set(hotListTotalKey,pageData.getTotalNum());
			list=pageData.getItems();
		}
		return list;
	}


	/**
	 * 获取直播总数
	 * @return
	 */
	public Integer  getHotListTotal(){
		Integer num=0;
		if(redisService.exists(hotListTotalKey)){
			num=(Integer) redisService.get(hotListTotalKey);
		}
		return num;
	}
	public void setHotListTotal(Integer num){
		redisService.set(hotListTotalKey,num);
	}


	/**
	 * 设置热点key
	 * @param tLiveInfo
	 */
	public void setHotKey(TLiveInfo tLiveInfo){
		redisService.set(hotKey,tLiveInfo);
	}
	/**
	 * 对于热点key的处理
	 * @return
	 */
	public TLiveInfo getHotKey(){
		if(redisService.exists(hotKey)){
			logger.debug("热点hot命中！");
			Object obj=redisService.get(hotKey);
			if(obj==null){
				return null;
			}else{
				return (TLiveInfo)obj;
			}

		}else{
			DistributedLock lock = DistributedLockUtil.getDistributedLock(hotKey);
			try {
				if (lock.acquire()) {
					TLiveInfo tLiveInfo=new TLiveInfo();
					List<TLiveInfo> list=this.appLiveIndex(tLiveInfo);
					if(list!=null&&list.size()>0){
						setHotKey(list.get(0));
						return list.get(0);
					}else{//不存在，放置半分钟的过期
						redisService.set(hotKey,tLiveInfo,30l);
					}
				}
			}finally {
				if (lock != null) {
					lock.release();
				}
			}
			return null;
		}
	}

	/**
	 * 后台管理员对直播的每一步操作都会更新热点key
	 */
	public void adminSetHotKey(){
		TLiveInfo tLiveInfo=new TLiveInfo();
		List<TLiveInfo> list=this.appLiveIndex(tLiveInfo);
		if(list!=null&&list.size()>0){
			setHotKey(list.get(0));
		}
	}


	public int queryMaxId(){
		return tLiveInfoMapper.queryMaxId();
	}
	public int addLive(TLiveInfo tLiveInfo){
		return tLiveInfoMapper.addLive(tLiveInfo);
	}
	public List<TLiveInfo> getAdminList(TLiveInfo tLiveInfo,int pageSize,int pageNo){
		return tLiveInfoMapper.getAdminList(tLiveInfo);
	}

	public List<TLiveInfo> appLiveIndex(TLiveInfo tLiveInfo){
		return tLiveInfoMapper.appLiveIndex(tLiveInfo);
	}
	public List<TLiveInfo> getAdminListPage(Map<String,String> map, int pageNo, int pageSize){
		PageHelper.startPage(pageNo, pageSize);
		List<TLiveInfo> list=tLiveInfoMapper.getAdminListPage(map);
		int total=0;
		if(list!=null){
			total=list.size();
		}
		PageBean<TLiveInfo> pageData = new PageBean<>(pageNo,pageSize,total);
		pageData.setItems(list);
		List<TLiveInfo> thisList=pageData.getItems();
		return thisList;
	}
	public PageBean<TLiveInfo>  getAdminListPageV2(Map<String,String> map, int pageNo, int pageSize){
		PageHelper.startPage(pageNo, pageSize);
		List<TLiveInfo> list=tLiveInfoMapper.getAdminListPage(map);
		int total=tLiveInfoMapper.getAdminListPageSize(map);
		PageBean<TLiveInfo> pageData = new PageBean<>(pageNo,pageSize,total);
		pageData.setItems(list);
		return pageData;
	}



	public void updateBeginOrEnd(TLiveInfo tLiveInfo){
		tLiveInfoMapper.updateBeginOrEnd(tLiveInfo);
	}
	public void deleteLive(int id){
		tLiveInfoMapper.deleteLive(id);
	}

	public List<TLiveInfo> findStartLiveList() {
		return  tLiveInfoMapper.findStartLiveList();
	}

	public List<TLiveInfo> getAllLive() {
		return tLiveInfoMapper.getAllLive();
	}

	public TLiveInfo getLiveInfoById(int liveId) {
		return tLiveInfoMapper.getLiveInfoById(liveId);
	}

	/**
	 * 根据直播频道id查找直播id
	*@Author: 90
	*@Description
	*@Param  * @param null
	*@Date 17:53 2018/8/7
	*/
	public String getIdByChannelId(String channelId){
		List<Map<String,Object>> list=tLiveInfoMapper.getIdByChannelId(channelId);
		if(list!=null&&list.size()>0){
			Map<String,Object> thisMap=list.get(0);
			return thisMap.get("ID").toString();
		}
		return "";
	}
	/**
     * 获得直播对象
	*@Author: 90
	*@Description
	*@Param  * @param null
	*@Date 20:12 2018/8/7
	*/
    public TLiveInfo get(String id){
	    return tLiveInfoMapper.get(id);
    }

    /**
	 * 根据推流地址生成频道id
    *@Author: 90
    *@Description
    *@Param  * @param null
    *@Date 10:09 2018/8/8
    */
	public  String getChannelIdFromObs(String obsUrl) {
		String channelId = "";
		int a = obsUrl.lastIndexOf("live/");
		int b = obsUrl.indexOf("?bizid");
		if (a!=-1&&b!=-1) {
			channelId = obsUrl.substring(a+5, b);
		}
		return channelId;
	}

	/**
	 * app端获取直播的更多，分页解析
	*@Author: 90
	*@Description
	*@Param  * @param null
	*@Date 10:26 2018/8/8
	*/
	public PageBean<TLiveInfo>  getAppLiveList(Map<String,String> map, int pageNo, int pageSize){
		PageHelper.startPage(pageNo, pageSize);
		List<TLiveInfo> list=tLiveInfoMapper.getAppLiveList(map);
		int total=tLiveInfoMapper.getAppLiveListSize(map);
		PageBean<TLiveInfo> pageData = new PageBean<>(pageNo,pageSize,total);
		pageData.setItems(list);
		return pageData;
	}
	public void updateLive(TLiveInfo tLiveInfo){
		tLiveInfoMapper.updateLive(tLiveInfo);
	}

	/**
	 * 单个直播存入缓存
	 * @param tLiveInfo
	 */
	public void setOneCacheLive(TLiveInfo tLiveInfo){
        redisService.set(preyKey+"id."+tLiveInfo.getId(),tLiveInfo,2*24*60*60l);
	}

	/**
	 * 从缓存中取得直播
	 * @param id
	 * @return
	 */
	public TLiveInfo getOneCacheLive(String id){
         if(redisService.exists(preyKey+"id."+id)){
             return  (TLiveInfo)redisService.get(preyKey+"id."+id);
		 }else{
			 TLiveInfo tLiveInfo=this.get(id);
			 if(tLiveInfo!=null){
				 this.setOneCacheLive(tLiveInfo);
				 return tLiveInfo;
			 }else{
			 	return null;
			 }

		 }
	}
	public void delOneCacheLive(String id){
		redisService.remove(preyKey+"id."+id);
	}
}
