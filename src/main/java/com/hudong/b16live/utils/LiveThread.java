package com.hudong.b16live.utils;

import com.hudong.b16live.service.RedisService;

/**
 * LiveThread
 *
 * @Title: LiveThread.java
 * @Copyright: Copyright (c) 2005
 * @Description:
 * @Company: 百科
 * @Created on 2018/8/8 12:48
 * @Author 90
 */
public class LiveThread implements Runnable{

    private  String key;
    public LiveThread(){

    }
    public LiveThread(String key){
         this.key=key;
    }

    private volatile boolean isStopped = false;
    RedisService redisService=(RedisService)SpringUtil.getBean("redisService");
    public void run(){
        while(!isStopped){
            System.out.println("线程--------------"+redisService.get("key"));
            System.out.println("Game thread is running......");
            System.out.println("Game thread is now going to pause");
            try{
                Thread.sleep(200);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
            if(!redisService.exists(key)){//内存中没有了这个key，结束
               stop();
            }
            System.out.println("Game thread is now resumed......");
        }

        System.out.println("Game thread is stopped......");
    }
    public void stop(){
        isStopped = true;
    }
}