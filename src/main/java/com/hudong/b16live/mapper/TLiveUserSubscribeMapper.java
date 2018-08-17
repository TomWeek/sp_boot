package com.hudong.b16live.mapper;


import com.hudong.b16live.bean.TLiveUserSubscribe;
import com.hudong.b16live.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Dao层接口
 * @author Lenovo
 * 2018-08-03 11:08:10
 */
public interface TLiveUserSubscribeMapper extends MyMapper<TLiveUserSubscribe> {

	/**
	 * 添加记录
	 * @param tLiveUserSubscribe
	 */
	public void saveTLiveUserSubscribe(@Param("bean") TLiveUserSubscribe tLiveUserSubscribe);
	
	/**
	 * 删除记录
	 * @param tLiveUserSubscribe
	 */
	public void removeTLiveUserSubscribe(@Param("bean") TLiveUserSubscribe tLiveUserSubscribe);

	/**
	 * 获取订阅记录
	 * @param tLiveUserSubscribe
	 * @return
	 */
	TLiveUserSubscribe getTLiveUserSubscribe(@Param("bean") TLiveUserSubscribe tLiveUserSubscribe);

	/**
	 * 通过直播ID获取订阅列表
	 * @param liveId
	 * @return
	 */
	List<TLiveUserSubscribe> getSubscribeListByLiveId(@Param("liveId") Integer liveId);



	void updateTLiveUserSubscribe(@Param("bean") TLiveUserSubscribe tLiveUserSubscribe);

	/**
	 * 更新推送状态
	 * @param liveId
	 */
	void updateSubscribeListByLiveId(@Param("liveId") Integer liveId);
}
