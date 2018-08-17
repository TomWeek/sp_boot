package com.hudong.b16live.utils;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author zhangjianwei
 * @title 封装极光推送消息API
 * @Description: <br>
 * <br>
 * @Company: 在线
 * @Created on 2018/8/8.
 */
@Component
public class JiGuangPushMessageAPI {
//    private static final String masterSecret = "64079f731a483bb40e8b9984";//"05f64f54a7fee7596d21a79b";
//    private static final String appKey = "2c650d0f1827175bd70d2312";//"316262639a10d65177f2f4ac";

    private static final String masterSecret = "4281231e9fd9f5c0940c2176";
    private static final String appKey = "9272471027877fff8dc3b5a8";


    /**
     * 发送Jpush消息接口
     * @param type 1广播（推送给一组） 2推送给指定用户
     * @param aliasOrTag 推送标记
     * @param alert 任务栏消息提示
     * @param map	非必要参数集合如 userName:zhangsan  userId:1001
     * @return
     */
    public static boolean  pushMsg(int type, String aliasOrTag, String alert, Map<String, String> map){
        boolean isSuccess = false;
        PushPayload pushPayload = buildPushPayload(type, aliasOrTag, alert, map);
        JPushClient jpushClient = new JPushClient(masterSecret, appKey, 3);
        try {
            jpushClient.sendPush(pushPayload);
            isSuccess = true;
        } catch (Exception e) {
        }
        return isSuccess;
    }

    /**
     * 发送Jpush消息接口
     * @param type 1广播（推送给一组） 2推送给指定一批用户(最多1000个)
     * @param aliasOrTag 推送标记
     * @param alert 任务栏消息提示
     * @param map	非必要参数集合如 userName:zhangsan  userId:1001
     * @return
     */
    public static boolean pushMsg(int type, List<String> aliasOrTag, String alert, Map<String, String> map){
        boolean isSuccess = false;
        PushPayload pushPayload = buildPushPayload(type, aliasOrTag, alert, map);
        JPushClient jpushClient = new JPushClient(masterSecret, appKey, 3);
        try {
            jpushClient.sendPush(pushPayload);
            isSuccess = true;
        } catch (Exception e) {
        }
        return isSuccess;
    }


    /**
     * 构建极光推送参数
     * @param type 1广播（推送给一组） 2推送给指定用户
     * @return
     */
    public static PushPayload buildPushPayload(int type, Object aliasOrTag, String alert, Map<String, String> map) {
        cn.jpush.api.push.model.PushPayload.Builder builder = new cn.jpush.api.push.model.PushPayload.Builder();
        if(type == 1){//广播
            builder.setAudience(Audience.all());
        }else{
            if(aliasOrTag instanceof Collection){
                builder.setAudience(Audience.newBuilder().addAudienceTarget(AudienceTarget.alias( (Collection<String>) aliasOrTag )).build());
            }else {
                builder.setAudience(Audience.newBuilder().addAudienceTarget(AudienceTarget.alias( String.valueOf( aliasOrTag ) )).build());
            }

        }
        cn.jpush.api.push.model.notification.IosNotification.Builder iosNBuilder =IosNotification.newBuilder();
        iosNBuilder.setAlert(alert).setSound("happy.caf").setBadge(1);
        iosNBuilder.setAlert(alert).addExtra("from", "JPush");
        cn.jpush.api.push.model.notification.AndroidNotification.Builder adBuilder = AndroidNotification.newBuilder();
        adBuilder.setAlert(alert).setTitle("石榴财经").addExtra("from", "JPush");

        if(map != null){
            Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                iosNBuilder.addExtra(entry.getKey(), entry.getValue());
                adBuilder.addExtra(entry.getKey(), entry.getValue());
            }
        }

        builder.setPlatform(Platform.android_ios())
                .setNotification(
                        Notification
                                .newBuilder()
                                .addPlatformNotification(
                                        iosNBuilder.build())
                                .addPlatformNotification(
                                        adBuilder.build())
                                .build())
                .setOptions(Options.newBuilder()
                        .setApnsProduction(true)
                        .build())
                .setMessage(Message.newBuilder()
                        .setMsgContent("")
                        .addExtra("from", "JPush")
                        .build());
        return builder.build();
    }
}
