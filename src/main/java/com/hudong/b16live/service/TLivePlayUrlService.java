package com.hudong.b16live.service;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.hudong.b16live.bean.TLivePlayUrl;
import com.hudong.b16live.mapper.TLivePlayUrlMapper;
import com.hudong.b16live.utils.RedisKeyConstant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * dao实现层
 * @author Lenovo
 * 2018-08-03 11:08:10
 */
@Service
public class TLivePlayUrlService {

	@Autowired
	private TLivePlayUrlMapper tLivePlayUrlMapper;
	@Autowired
	RedisService redisService;
	
	//添加记录
	public void saveTLivePlayUrl(TLivePlayUrl tLivePlayUrl) {
		tLivePlayUrlMapper.saveTLivePlayUrl(tLivePlayUrl);
	}
	//根据直播间id查询录制文件数量
	public int countByLiveId(int liveId) {
		return tLivePlayUrlMapper.countByLiveId(liveId);
	}
	//根据直播间id查找播放地址信息
	public TLivePlayUrl queryByLiveId(int liveId) {
		List<TLivePlayUrl> list = tLivePlayUrlMapper.queryByLiveId(liveId);
		if (list!=null && list.size()>0) {
			return list.get(0);
		}
		return null;
	}
	//根据视频拼接任务id查询录制文件
	public TLivePlayUrl queryByTaskId(String taskId) {
		List<TLivePlayUrl> list = tLivePlayUrlMapper.queryByTaskId(taskId);
		if (list!=null && list.size()>0) {
			return list.get(0);
		}
		return null;
	}
	//根据直播间id查询录制文件地址
	public String getUrlListByLiveId(int liveId) {
		String video = "";
		try {
			//从redis中取数据
			video = (String)redisService.get(RedisKeyConstant.VIDEO_LIST_KEY+liveId);
			if (video==null || "".equals(video)) {
				List<TLivePlayUrl> list = tLivePlayUrlMapper.queryByLiveId(liveId);
				if (list!=null && list.size()>0) {
					video = list.get(0).getPlayUrl();
					//向redis中插入数据
					redisService.set(RedisKeyConstant.VIDEO_LIST_KEY+liveId,video);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return video;
	}
	
	//删除记录
	public void removeTLivePlayUrl(TLivePlayUrl tLivePlayUrl) {
	}
	//更新记录
	public void updateTLivePlayUrl(TLivePlayUrl tLivePlayUrl) {
		tLivePlayUrlMapper.updateTLivePlayUrl(tLivePlayUrl);
	}

}
