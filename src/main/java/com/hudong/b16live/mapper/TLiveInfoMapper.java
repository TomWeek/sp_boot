package com.hudong.b16live.mapper;

import java.util.List;
import java.util.Map;

import com.hudong.b16live.bean.TLiveInfo;
import com.hudong.b16live.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

/**
 * Dao层接口
 * @author Lenovo
 * 2018-08-03 11:08:10
 */
public interface TLiveInfoMapper  extends MyMapper<TLiveInfo>{



	public int queryMaxId();
	public int addLive(TLiveInfo tLiveInfo);
	public List<TLiveInfo> getAdminList(TLiveInfo tLiveInfo);
	public List<TLiveInfo> appLiveIndex(TLiveInfo tLiveInfo);
	public List<TLiveInfo> getAdminListPage(Map<String,String> map);
	public void updateBeginOrEnd(TLiveInfo tLiveInfo);
	public void deleteLive(int id);
	public int getAdminListPageSize(Map<String,String> map);

    public List<TLiveInfo> findStartLiveList();

	public List<TLiveInfo> getAllLive();
	public List<Map<String,Object>> getIdByChannelId(String channelId);

	public TLiveInfo get(String id);

	public List<TLiveInfo> getAppLiveList(Map<String,String> map);
	public int getAppLiveListSize(Map<String,String> map);


	TLiveInfo getLiveInfoById(@Param("liveId") int liveId);

	public void updateLive(TLiveInfo tLiveInfo);
}
