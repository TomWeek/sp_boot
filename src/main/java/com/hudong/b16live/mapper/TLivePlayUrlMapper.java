package com.hudong.b16live.mapper;

import com.hudong.b16live.bean.TLivePlayUrl;
import com.hudong.b16live.utils.MyMapper;

import java.io.Serializable;
import java.util.List;



/**
 * Dao层接口
 * @author Lenovo
 * 2018-08-03 11:08:10
 */
public interface TLivePlayUrlMapper extends MyMapper<TLivePlayUrl> {

	//添加记录
	public void saveTLivePlayUrl(TLivePlayUrl tLivePlayUrl);
	//更新记录
	public void updateTLivePlayUrl(TLivePlayUrl tLivePlayUrl);	
	//根据直播间id查询录制文件数量
	public int countByLiveId(int liveId);
	//根据直播间id查询录制文件列表
	public List<TLivePlayUrl> queryByLiveId(int liveId);
	//根据视频拼接任务id查询录制文件列表
	public List<TLivePlayUrl> queryByTaskId(String taskId);
}
