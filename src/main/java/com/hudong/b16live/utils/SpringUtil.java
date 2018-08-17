package com.hudong.b16live.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

 /**
  * @description：普通类调用Spring bean对象：此类需要放到App.java同包或者子包下才能被扫描，否则失效。
  * @author：JBL
  * @date：2017/12/12 13:15
  */
@Component
public class SpringUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringUtil.applicationContext == null) {
            SpringUtil.applicationContext = applicationContext;
        }
    }

    /**
     * 获取applicationContexts
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过name获取 Bean
     * @param name
     * @return
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean( name );
    }

     /**
      * 通过class获取Bean
      * @param clazz
      * @param <T>
      * @return
      */
    public static<T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean( clazz );
    }

     /**
      * 通过name,以及Clazz返回指定的Bean
      * @param name
      * @param clazz
      * @param <T>
      * @return
      */
    public static<T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean( name, clazz );
    }
}