package com.hudong.b16live.controller;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.hudong.b16live.bean.AppParams;
import com.hudong.b16live.bean.BaseUser;
import com.hudong.b16live.bean.TLiveUser;
import com.hudong.b16live.service.*;
import com.hudong.b16live.utils.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户
 * @author Lenovo
 */
@Controller
public class TLiveUserController extends BaseController {
	/**
	 * 日志打印
	 */
	private static final Logger logger = LoggerFactory.getLogger(TLiveUserController.class);

	@Autowired
	private TLiveUserService tLiveUserService;
	public static final String SESSION_USER_KEY = "session_user";
	//登录页
	@RequestMapping(value = "/")
	public String login(HttpServletRequest request){
		return "/login";
	}
	//登录鉴权
	@RequestMapping(value = "/login")
	@ResponseBody
	public String checkLogin(HttpServletRequest request) throws IOException{
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			password = MD5Util.encode2MD5(password);
			TLiveUser tLiveUser = tLiveUserService.queryByName(username);
			if (tLiveUser==null) {
				resultMap.put("code", "0");
				resultMap.put("msg", "不存在账户"+username);
			}else {
				if (password.equals(tLiveUser.getPassword())) {//校验登录信息
					HttpSession session = request.getSession();
					session.setAttribute(SESSION_USER_KEY, tLiveUser);
					session.setMaxInactiveInterval(300*60);//10分钟过时
					logger.info(username+"登陆成功");
					resultMap.put("code", "1");
					resultMap.put("msg", "登陆成功");
				}else {
					resultMap.put("code", "0");
					resultMap.put("msg", "密码错误");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			resultMap.put("code", "0");
			resultMap.put("msg", "登陆失败"+e.getMessage());
		}
		String result = JSON.toJSONString(resultMap);
		return result;
	}
	//登出
	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request){
		HttpSession session = request.getSession();
		if (session!=null) {
			TLiveUser tLiveUser = (TLiveUser)session.getAttribute(SESSION_USER_KEY);
			session.invalidate();
			logger.info(tLiveUser.getUsername()+"登出");
		}
		return "/login";
	}
	//跳转到修改密码页
	@RequestMapping("/password")
	public String password(HttpServletRequest request){
		return "/TLiveUser/password";
	}
	//修改密码
	@RequestMapping("/modifypass")
	@ResponseBody
	public String modifypass(HttpServletRequest request){
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			HttpSession session = request.getSession();
			if (session!=null) {
				TLiveUser tLiveUser = (TLiveUser)session.getAttribute(SESSION_USER_KEY);
				String oldPwd = request.getParameter("oldPwd");//旧密码
				String newPwd = request.getParameter("newPwd");//新密码
				String rePwd = request.getParameter("rePwd");//重新输入新密码
				oldPwd = MD5Util.encode2MD5(oldPwd);
				if (tLiveUser.getPassword().equals(oldPwd)) {
					if (newPwd.equals(rePwd)) {
						tLiveUser.setPassword(MD5Util.encode2MD5(newPwd));
						tLiveUserService.updateUser(tLiveUser);
						resultMap.put("code", "1");
						resultMap.put("msg", "密码修改成功");
					}else {
						logger.error("新密码与第二次输入密码不同");
						resultMap.put("code", "0");
						resultMap.put("msg", "新密码与第二次输入密码不同");
					}
				}else {
					logger.error("旧密码有误");
					resultMap.put("code", "0");
					resultMap.put("msg", "旧密码有误");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			resultMap.put("code", "0");
			resultMap.put("msg", "密码修改失败"+e.getMessage());
		}
		String result = JSON.toJSONString(resultMap);
		return result;
	}

}
