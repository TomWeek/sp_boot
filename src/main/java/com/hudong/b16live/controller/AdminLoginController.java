package com.hudong.b16live.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.hudong.b16live.utils.AdminUserTemp;

@Controller
public class AdminLoginController {

	private static final Logger logger = Logger.getLogger(AdminLoginController.class);
	//登录页
	/*@RequestMapping(value = "/")
	public String login(HttpServletRequest request){
		return "/login";
	}
	
	//登录鉴权
	@RequestMapping(value = "/login")
	@ResponseBody
	public String checkLogin(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		AdminUserTemp tempUser = new AdminUserTemp(username,password);
		if (AdminUserTemp.checkTempUser(tempUser)) {//校验登录信息
			HttpSession session = request.getSession();
			session.setAttribute(AdminUserTemp.SESSION_USER_KEY, tempUser);
			session.setMaxInactiveInterval(300*60);//10分钟过时
			logger.info(tempUser.getUsername()+"登陆成功");
			return "success";
		}
		logger.info(tempUser.getUsername()+"登陆失败");
		return "failed";
	}
	
	//登出
	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request){
		HttpSession session = request.getSession();
		if (session!=null) {
			AdminUserTemp tempUser = (AdminUserTemp)session.getAttribute(AdminUserTemp.SESSION_USER_KEY);
			session.invalidate();
			logger.info(tempUser.getUsername()+"登出");
		}
		return "/login";
	}*/
	
}
