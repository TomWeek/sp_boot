package com.hudong.b16live.service;


import com.hudong.b16live.bean.TLiveUserSubscribe;
import com.hudong.b16live.mapper.TLiveUserSubscribeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * dao实现层
 * @author Lenovo
 * 2018-08-03 11:08:10
 */
@Service
public class TLiveUserSubscribeService {

	@Autowired
	private TLiveUserSubscribeMapper tLiveUserSubscribeMapper;
	/**
	 * 添加记录
	 * @param tLiveUserSubscribe
	 */

	public void saveTLiveUserSubscribe(TLiveUserSubscribe tLiveUserSubscribe) {

		tLiveUserSubscribeMapper.saveTLiveUserSubscribe(tLiveUserSubscribe);

	}

	public TLiveUserSubscribe getTLiveUserSubscribe(TLiveUserSubscribe tLiveUserSubscribe) {

		return tLiveUserSubscribeMapper.getTLiveUserSubscribe(tLiveUserSubscribe);
	}

	public List<TLiveUserSubscribe> getSubscribeListByLiveId(Integer liveId) {
		List<TLiveUserSubscribe> list=tLiveUserSubscribeMapper.getSubscribeListByLiveId(liveId);
		tLiveUserSubscribeMapper.updateSubscribeListByLiveId(liveId);
		return list;
	}

	public void cancelSubscribe(TLiveUserSubscribe tLiveUserSubscribe) {
        if(tLiveUserSubscribe.getLiveId()!=null&&tLiveUserSubscribe.getUserId()!=null){
			tLiveUserSubscribeMapper.removeTLiveUserSubscribe(tLiveUserSubscribe);
		}
	}

	public void updateTLiveUserSubscribe(TLiveUserSubscribe tLiveUserSubscribe) {
		tLiveUserSubscribeMapper.updateTLiveUserSubscribe(tLiveUserSubscribe);
	}

}
