package com.hudong.b16live.service;


import com.hudong.b16live.bean.TLiveInfo;
import com.hudong.b16live.bean.TLiveUserSubscribe;
import com.hudong.b16live.utils.JiGuangPushMessageAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * dao实现层
 * @author Lenovo
 * 2018-08-03 11:08:10
 */
@Service
public class PushMessageService {

	@Autowired
	private TLiveUserSubscribeService tLiveUserSubscribeService;
	@Value("${push.userpre}")
	private  String pushUserPre;

	public void sendMessageToSubscribeUser(TLiveInfo ti) {

		Integer liveId=ti.getId();
		String liveName=ti.getTitle();
		String adAlert="您预约的直播「"+liveName+"」马上开始了,立即围观>>";
		List<TLiveUserSubscribe> subscribeList=tLiveUserSubscribeService.getSubscribeListByLiveId(liveId);
		List<String> aliaslist = new ArrayList<>();
		if (subscribeList != null && !subscribeList.isEmpty()) {
			for (int i = 0; i < subscribeList.size(); i++) {
				aliaslist.add( pushUserPre + subscribeList.get(i).getUserId());
			}
		}
		HashMap<String, String> push_map = new HashMap<>();
		push_map.put( "type", "1" );
		push_map.put( "liveId", String.valueOf(liveId ) );
		push_map.put( "body", adAlert );
		push_map.put( "title", adAlert );
		//分批处理
		if (null != aliaslist && aliaslist.size() > 0) {
			//限制条数
			int dataLimit = 500;
			Integer size = aliaslist.size();
			//判断是否有必要分批
			if (dataLimit < size) {
				//分批数
				int part = size / dataLimit;
				for (int i = 0; i < part; i++) {
					//1000条
					List<String> listPage = aliaslist.subList( 0, dataLimit );
					//JiGuangPushMessageAPI.pushMsg( 2, listPage, adAlert, push_map );
					//剔除
					aliaslist.subList( 0, dataLimit ).clear();
				}

				if (!aliaslist.isEmpty()) {
					//表示最后剩下的数据
					//JiGuangPushMessageAPI.pushMsg( 2, aliaslist, adAlert, push_map );
				}
			} else {
				//不足500条
				//JiGuangPushMessageAPI.pushMsg( 2, aliaslist, adAlert, push_map );
			}
		}
	}

}
