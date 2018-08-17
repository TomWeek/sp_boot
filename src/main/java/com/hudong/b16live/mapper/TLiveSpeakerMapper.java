package com.hudong.b16live.mapper;

import com.hudong.b16live.bean.TLiveSpeaker;
import com.hudong.b16live.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * Dao层接口
 * @author Lenovo
 * 2018-08-03 11:08:10
 */
public interface TLiveSpeakerMapper extends MyMapper<TLiveSpeaker> {

	/**
	 * 添加记录
	 * @param tLiveSpeaker
	 */
	public void saveTLiveSpeaker(@Param("bean") TLiveSpeaker tLiveSpeaker);

	/**
	 * 获取主讲记录
	 * @param liveId
	 * @return
	 */
    List<TLiveSpeaker> findSpeakerRecordByLiveId(@Param("liveId") int liveId);
}
