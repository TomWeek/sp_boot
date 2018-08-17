package com.hudong.b16live.utils;

/**
 * @author zhangjianwei
 * @title
 * @Description: <br>
 * <br>
 * @Company: 在线
 * @Created on 2018/8/10.
 */
public class RedisKeyConstant {

    /**
     * 在线人数统计key
     * 以map的方式保存直播的在线人数
     */
    public static final String ONLINE_COUNT_KEY = "b16live.online";
    /**
     * 发送极光推送消息分布式锁
     */
    public static final String PUSH_MSG_LOCK_KEY = "b16live.msgLock";
    /**
     *同步在线人数分布式锁
     */
    public static final String SYNC_ONLINE_COUNT_LOCK_KEY = "b16live.onlineLock";

    public static final String VIDEO_LIST_KEY = "b16live.videoList";

}
