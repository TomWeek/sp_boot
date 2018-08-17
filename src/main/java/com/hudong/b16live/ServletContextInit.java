package com.hudong.b16live;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 *	设置全局变量，应用上下文
 */
@Component
public class ServletContextInit implements InitializingBean {

	@Autowired
	private WebApplicationContext webappContext;

	@Override
	public void afterPropertiesSet() throws Exception {
		webappContext.getServletContext().setAttribute("testApplication", "testApplicationValue");
		webappContext.getServletContext().setAttribute("ctx", webappContext.getApplicationName());
	}

}
