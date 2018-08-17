package com.hudong.b16live.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * SessionConfig
 *
 * @Title: SessionConfig.java
 * @Copyright: Copyright (c) 2005
 * @Description:
 * @Company: 百科
 * @Created on 2018/8/14 11:15
 * @Author 90
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 18000)
public class SessionConfig {


}
