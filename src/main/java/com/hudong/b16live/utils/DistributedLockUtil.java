package com.hudong.b16live.utils;

import com.hudong.b16live.utils.lock.DistributedLock;
import com.hudong.b16live.utils.lock.JedisLock;

public class DistributedLockUtil{

    /**
     * 获取分布式锁
     * 默认获取锁10s超时，锁过期时间60s
     * @return
     */
    public static DistributedLock getDistributedLock(String lockKey){
        JedisLock lock = new JedisLock(lockKey);
        return lock;
    }


    /**
     * 获取分布式锁
     * 默认获取锁10s超时，锁过期时间60s
     * @param lockKey
     * @param timeoutMsecs 指定获取锁超时时间
     * @return
     */
    public static DistributedLock getDistributedLock(String lockKey,int timeoutMsecs){
        JedisLock lock = new JedisLock(lockKey,timeoutMsecs);
        return lock;
    }

    /**
     * 获取分布式锁
     * 默认获取锁10s超时，锁过期时间60s
     * @param lockKey 锁的key
     * @param timeoutMsecs 指定获取锁超时时间
     * @param expireMsecs 指定锁过期时间
     * @return
     */
    public static DistributedLock getDistributedLock(String lockKey,int timeoutMsecs,int expireMsecs){
        JedisLock lock = new JedisLock(lockKey,expireMsecs,timeoutMsecs);
        return lock;
    }

}