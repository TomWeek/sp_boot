package com.hudong.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hudong.b16live.bean.TLiveUser;
import java.io.*;

/**
 * Created by WQS on 2018/8/6.
 */
@WebFilter(filterName = "sessionFilter",urlPatterns = {"/*"})
public class CheckFilter implements Filter {
    //标示符：表示当前用户未登录(可根据自己项目需要改为json样式)
    String NO_LOGIN = "您还未登录";
    public static final String SESSION_USER_KEY = "session_user";
    //不需要登录就可以访问的路径(比如:注册、登录、对外暴露接口等)
    String[] includeUrls = new String[]{
    		"/",
    		"/login",//登录页
    		"register",//注册
    		"/v1/playCallBack",//事件消息通知回调接口
    		"/v1/playUrl",//直播回放视频地址列表接口
    		"/v1/indexLive",//直播首页接口
    		"/v1/liveList",//直播列表接口
    		"/v1/oneLive",//直播详情页接口
    		"/v1/setSub",//预约/取消接口
            "/subscribe/submit.do",//订阅接口
            "/subscribe/isSubscribe.do",//订阅状态获取接口
            "/imMessage/list.do"//主讲记录接口
    		};
    //不过滤的静态资源
    String[] fileTypes = new String[]{".js",".css",".html",".jpg",".jepg",".png",".gif",".txt",".ico",".ftl",".xls",".doc"};
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = request.getRequestURI();
        //是否需要过滤
        boolean needFilter = isNeedFilter(uri);
        if (!needFilter) { //不需要过滤直接传给下一个过滤器
            filterChain.doFilter(servletRequest, servletResponse);
        }else{
        	HttpSession session = request.getSession();
        	TLiveUser tLiveUser = (TLiveUser)session.getAttribute(SESSION_USER_KEY);
            if (tLiveUser!=null) {
                filterChain.doFilter(servletRequest, servletResponse);
            }else {//跳转到登录页
                response.sendRedirect("/");
            }
        }
    }
    public boolean isNeedFilter(String uri) {
    	for (String fileType : fileTypes) {
    		if (uri.indexOf(fileType)>-1) {//静态资源
        		return false;
    		}
		}
        for (String includeUrl : includeUrls) {//非登陆状态可访问uri
            if(includeUrl.equals(uri)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void destroy() {

    }

}
