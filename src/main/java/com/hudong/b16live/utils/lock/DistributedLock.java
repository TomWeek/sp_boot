package com.hudong.b16live.utils.lock;

public interface DistributedLock {

    /**
     * 获取锁
     * @return
     * @throws InterruptedException
     */
    public boolean acquire();

    /**
     * 释放锁
     */
    public void release();

}