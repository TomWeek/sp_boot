package com.hudong.b16live.controller;

import javax.servlet.http.HttpServletRequest;

import com.hudong.b16live.service.TLiveSpeakerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *  控制层
 * @author Lenovo
 *
 */
@Controller
public class TLiveSpeakerController  extends BaseController{

	/**
	 * 日志打印
	 */
	private static final Logger logger = Logger.getLogger(TLiveSpeakerController.class);
	
	@Autowired
	private TLiveSpeakerService tLiveSpeakerServiceImp;

	@RequestMapping("/v1/addNews")
	public String  addNews(HttpServletRequest request){

		return "/TLiveSpeaker/addNews";
	}
	
}
