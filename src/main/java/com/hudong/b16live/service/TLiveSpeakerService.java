package com.hudong.b16live.service;


import com.hudong.b16live.bean.TLiveSpeaker;
import com.hudong.b16live.mapper.TLiveSpeakerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * dao实现层
 * @author Lenovo
 * 2018-08-03 11:08:10
 */
@Service
public class TLiveSpeakerService {

	@Autowired
	private TLiveSpeakerMapper tLiveSpeakerMapper;
	/**
	 * 添加记录
	 * @param tLiveSpeaker
	 */

	public void saveTLiveSpeaker(TLiveSpeaker tLiveSpeaker) {
         tLiveSpeakerMapper.saveTLiveSpeaker(tLiveSpeaker);
	}

    public List<TLiveSpeaker> findSpeakerRecordByLiveId(int liveId) {
		return tLiveSpeakerMapper.findSpeakerRecordByLiveId(liveId);
    }
}
