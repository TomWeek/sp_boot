package com.hudong.b16live.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hudong.b16live.bean.TLivePlayUrl;
import com.hudong.b16live.service.TLiveImService;
import com.hudong.b16live.service.TLiveInfoService;
import com.hudong.b16live.service.TLivePlayUrlService;
import com.hudong.b16live.utils.LiveEventEnums;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * @author liufanhua
 * 直播视频地址
 */
@Controller
public class TLivePlayUrlController  extends BaseController{

	/**
	 * 日志打印
	 */
	private static final Logger logger = LoggerFactory.getLogger(TLivePlayUrlController.class);
	public static final String VIDEO_CONCAT_TYPE = "ConcatComplete";//视频拼接完成
	
	@Autowired
	private TLivePlayUrlService tLivePlayUrlService;
	@Autowired
	private TLiveInfoService tLiveInfoService;
	@Autowired
    private TLiveImService tLiveImService;
	
	//腾讯云一个新录制文件的生成回调接口
	@RequestMapping(path = "/v1/playCallBack")
	@ResponseBody
	public Object playCallBack(@RequestBody Map<String, Object> params){
		logger.info("/v1/playCallBack入参："+params.toString());
		Integer eventType = (Integer)params.get("event_type");
		//String event_type = params.get("event_type");//事件类型： 0、1、100、200
		String videoConcatType = (String)params.get("eventType");//视频拼接事件类型
		String result = "{\"code\":0}";
		try {
			//int eventType = Integer.parseInt(event_type);
			if (eventType!=null&&eventType==LiveEventEnums.NEWVEDIO.getEventType()) {//100:新的录制文件已生成
				logger.info("新的录制文件生成事件回调");
				String channel_id = (String)params.get("channel_id");//直播码:标示事件源于哪一条直播流
				String file_id = (String)params.get("file_id");
				String video_id = (String)params.get("video_id");//点播用 vid
				String video_url = (String)params.get("video_url");//点播视频的下载地址
				//根据直播码查询直播表id
				String live_id = tLiveInfoService.getIdByChannelId(channel_id);
				if (!"".equals(live_id)) {
					int liveId = Integer.parseInt(live_id);
					//int count = tLivePlayUrlService.countByLiveId(liveId);//查询该直播间已存在的录制文件数量
					TLivePlayUrl tLivePlayUrl = tLivePlayUrlService.queryByLiveId(liveId);
					if (tLivePlayUrl==null) {
						TLivePlayUrl playUrl = new TLivePlayUrl();
						playUrl.setLiveId(liveId);
						playUrl.setPlayUrl(video_url);
						playUrl.setFileId(file_id);
						playUrl.setTaskId(null);
						playUrl.setOrderNum(0);
						tLivePlayUrlService.saveTLivePlayUrl(playUrl);//视频地址入库
						logger.info("视频地址入库,直播ID="+live_id);
					}else {
						String fileId = tLivePlayUrl.getFileId();
						//调用腾讯云拼接视频接口
						logger.info("调用腾讯云拼接视频接口");
						String taskId = tLiveImService.concatVideo(fileId,file_id);
						if (StringUtils.isNotBlank(taskId)) {
							logger.info("调用腾讯云拼接视频接口成功,直播ID="+live_id+",拼接任务ID="+taskId);
							tLivePlayUrl.setTaskId(taskId);
							tLivePlayUrlService.updateTLivePlayUrl(tLivePlayUrl);
						}
					}
				}
			}
			if (VIDEO_CONCAT_TYPE.equals(videoConcatType)) {//视频拼接完成
				logger.info("视频拼接完成回调");
				Object obj = params.get("data");
				LinkedHashMap<String, Object> data = (LinkedHashMap<String, Object>)obj;
				String vodTaskId = (String)data.get("vodTaskId");
				List<HashMap<String, Object>> fileInfo = (List<HashMap<String, Object>>)data.get("fileInfo");
				for (Object eachInfo : fileInfo) {
					HashMap<String, Object> info = (HashMap<String, Object>)eachInfo;
					Integer status = (Integer)info.get("status");//任务执行结果，0为成功
					String fileType = (String)info.get("fileType");//拼接出的文件类型,m3u8,mp4
					if ("mp4".equals(fileType)&&status==0) {
						String fileId = (String)info.get("fileId");//拼接出的文件的fileId
						String fileUrl = (String)info.get("fileUrl");//拼接出的文件url
						//根据vodTaskId找到直播视频列表，更新视频地址
						TLivePlayUrl tLivePlayUrl = tLivePlayUrlService.queryByTaskId(vodTaskId);
						if (tLivePlayUrl!=null) {
							tLivePlayUrl.setFileId(fileId);
							tLivePlayUrl.setPlayUrl(fileUrl);
							tLivePlayUrlService.updateTLivePlayUrl(tLivePlayUrl);
						}else {
							logger.error("视频拼接完成回调异常，ID为vodTaskId的拼接任务找不到对应的直播信息");
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return result;
	}
}
