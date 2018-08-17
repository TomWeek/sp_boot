package com.hudong.b16live.controller;

import java.io.*;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.hudong.b16live.bean.AppParams;
import com.hudong.b16live.bean.BaseUser;
import com.hudong.b16live.bean.TLiveInfo;
import com.hudong.b16live.service.*;
import com.hudong.b16live.utils.*;
import com.hudong.b16live.utils.excel.ExcelUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import static com.hudong.b16live.task.SendOnlineCountTask.GetRandomNum;


/**
 *  控制层
 * @author Lenovo
 *
 */
@Controller
@RequestMapping("/")
public class TLiveInfoController  extends BaseController {

	/**
	 * 日志打印
	 */
	private static final Logger logger = LoggerFactory.getLogger(TLiveInfoController.class);


	@Autowired
	private TLivePlayUrlService tLivePlayUrlService;
	
	@Autowired
	private TLiveInfoService tLiveInfoService;
    @Autowired
    private TLiveImService tLiveImService;
	@Autowired
	RedisService redisService;
	@Autowired
	PushMessageService pushMessageService;

	/**
     * 新建直播
	*@Author: 90
	*@Description
	*@Param
	*@Date 11:15 2018/8/13
	*/
	@RequestMapping("/v1/create")
	public String createLive(HttpServletRequest request){
		String id = request.getParameter("id");
		request.setAttribute("date", new Date());
		return "/TLiveInfo/create";
	}

    /**
     * 修改直播
     *@Author: 90
     *@Description
     *@Param
     *@Date 11:15 2018/8/13
     */
	@RequestMapping("/v1/edit")
	public String editLive(HttpServletRequest request){
		String id = request.getParameter("id");
		TLiveInfo liveInfo=tLiveInfoService.get(id);
		request.setAttribute("liveInfo",liveInfo);
		//request.setAttribute("startTime",DateUtil.parseStringFullDate(liveInfo.getStartTime()));
		return "/TLiveInfo/edit";
	}

	/**
     * 更新直播
	*@Author: 90
	*@Description
	*@Param
	*@Date 11:16 2018/8/13
	*/
	@RequestMapping("/v1/update")
	@ResponseBody
	public String updateLive(HttpServletRequest request){
		String id = request.getParameter("id");
		String title = request.getParameter("title");
		String startTime=request.getParameter("startTime");
		String zsxs=request.getParameter("zsxs");
		String obsUrl=request.getParameter("obsUrl");
		String detail=request.getParameter("detail");
		String isHF=request.getParameter("isHF");
		String imgV=request.getParameter("imgV");
		String imgH=request.getParameter("imgH");
		TLiveInfo tLiveInfo=tLiveInfoService.get(id);
		tLiveInfo.setDetail(detail);
		tLiveInfo.setIsHf(Integer.valueOf(isHF));
		/*tLiveInfo.setCreTime(new Date());*/
		tLiveInfo.setTitle(title);
		tLiveInfo.setDelFlag(0);
		tLiveInfo.setObsId(obsUrl);
		//tLiveInfo.setStatus(2);
		if(null!=imgV&&!"".equals(imgV)){
			tLiveInfo.setImgUrlV(imgV);
		}else{
			tLiveInfo.setImgUrlV(this.domainUrl+"/images/vdefault.png");

		}
		if(null!=imgH&&!"".equals(imgH)){
			tLiveInfo.setImgUrlH(imgH);
		}else{
			tLiveInfo.setImgUrlH(this.domainUrl+"/images/hdefault.png");
		}
		tLiveInfo.setOnlineNumZsxs(zsxs);
		tLiveInfo.setOnlineNum(Integer.parseInt(zsxs) + GetRandomNum(1, 9));
		/*try {
			tLiveInfo.setStartTime(DateUtil.parseFullDate(startTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}*/

		tLiveInfo.setChannelId(tLiveInfoService.getChannelIdFromObs(obsUrl));
		tLiveInfoService.updateLive(tLiveInfo);
		tLiveInfoService.setOneCacheLive(tLiveInfo);
		tLiveInfoService.adminSetHotKey();
		tLiveInfoService.setHotListKey(1);

		return null;
	}


	/**
     * 后台主页
	*@Author: 90
	*@Description
	*@Param
	*@Date 11:16 2018/8/13
	*/
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request){
		return "/index";
	}


	/**
     * 直播保存
	*@Author: 90
	*@Description
	*@Param
	*@Date 11:16 2018/8/13
	*/
	@RequestMapping("/v1/save")
	public String saveLive(HttpServletRequest request) throws ParseException {

		String title = request.getParameter("title");
		String startTime=request.getParameter("startTime");
		String zsxs=request.getParameter("zsxs");
		String obsUrl=request.getParameter("obsUrl");
		String detail=request.getParameter("detail");
		String isHF=request.getParameter("isHF");
		String imgV=request.getParameter("imgV");
		String imgH=request.getParameter("imgH");
		TLiveInfo tLiveInfo=new TLiveInfo();
		tLiveInfo.setCreTime(new Date());
		tLiveInfo.setDetail(detail);
		tLiveInfo.setModTime(new Date());
		tLiveInfo.setTitle(title);
		tLiveInfo.setDelFlag(0);
		tLiveInfo.setIsHf(Integer.valueOf(isHF));
		if(null!=imgV&&!"".equals(imgV)){
			tLiveInfo.setImgUrlV(imgV);
		}else{
			tLiveInfo.setImgUrlV(this.domainUrl+"/images/vdefault.png");

		}
		if(null!=imgH&&!"".equals(imgH)){
			tLiveInfo.setImgUrlH(imgH);
		}else{
			tLiveInfo.setImgUrlH(this.domainUrl+"/images/hdefault.png");
		}
        tLiveInfo.setOnlineNum(Integer.parseInt(zsxs) + GetRandomNum(1, 9));
		tLiveInfo.setObsId(obsUrl);
		tLiveInfo.setOnlineNumZsxs(zsxs);
		Date startTimeDate=DateUtil.parseFullDate(startTime);
		tLiveInfo.setStartTime(startTimeDate);
		if(startTimeDate.after(new Date())){//过期
			tLiveInfo.setStatus(2);
		}else{
			tLiveInfo.setStatus(3);//开始时间比当前时间少，说明已经结束

		}
		tLiveInfo.setChannelId(tLiveInfoService.getChannelIdFromObs(obsUrl));
		//int thisId=tLiveInfoService.queryMaxId()+1;//后台并发量不大的情形
        tLiveInfoService.addLive(tLiveInfo);//保存
		if(2==tLiveInfo.getStatus()){//未开始的时候才创建
			String dmId=tLiveImService.createGroupAVChatRoom(tLiveInfo.getId()+"_"+"dm");
			String zjId=tLiveImService.createGroupAVChatRoom(tLiveInfo.getId()+"_"+"zj","ChatRoom");
			tLiveInfo.setSpeakerGroupId(zjId);
			tLiveInfo.setDmGroupId(dmId);
			tLiveInfoService.updateLive(tLiveInfo);
		}

		tLiveInfoService.setOneCacheLive(tLiveInfo);
		tLiveInfoService.adminSetHotKey();
		tLiveInfoService.setHotListKey(1);
		return null;
	}

	/**
     * 管理员列表查看
	*@Author: 90
	*@Description
	*@Param
	*@Date 11:17 2018/8/13
	*/
	@RequestMapping("/v1/adminList")
	public String  adminList(HttpServletRequest request){

		TLiveInfo tLiveInfo=new TLiveInfo();
		int pageNo=1;
		int pageSize=10;
		List<TLiveInfo> dataList=tLiveInfoService.getAdminList(tLiveInfo,pageSize,pageNo);

		request.setAttribute("dataList",dataList);

		return "/TLiveInfo/adminList";
	}


    /**
     * 后台分页
    *@Author: 90
    *@Description
    *@Param
    *@Date 11:17 2018/8/13
    */
	@RequestMapping("/v1/adminListAjax")
	public String  adminListAjax(HttpServletRequest request){
		//根据用户传来的token判断用户
		/*String token="1212";
		BaseUser user=this.getBaseUser(token);*/
		String title=request.getParameter("title");
		String id=request.getParameter("id");
		String status=request.getParameter("status");
		String endTime=request.getParameter("endTime");
		String startTime=request.getParameter("startTime");
		String pageNoStr=request.getParameter("pageNo");
		int pageNo=1;
		if(null!=pageNoStr&&!"".equals(pageNoStr)){
			pageNo=Integer.valueOf(pageNoStr);
		}

		int pageSize=10;
		Map<String,String>map=new HashMap<String,String>();
		map.put("startTime",startTime);
		map.put("endTime",endTime);
		map.put("status",status);
		map.put("title",title);
		map.put("id",id);
		PageBean<TLiveInfo> pageData=tLiveInfoService.getAdminListPageV2(map,pageNo,pageSize);
		//List<TLiveInfo> dataList=tLiveInfoService.getAdminListPage(map,pageNo,pageSize);
		pageData.goToPage(pageNo);//跳转到当前页
		request.setAttribute("dataList",pageData.getItems());
		request.setAttribute("pageHtml",pageData.getPageCode());//分页样式
		request.setAttribute("total",pageData.getTotalNum());
		/*LiveThread thread=new LiveThread("key");
		Thread t1 = new Thread(thread, "T1");
		t1.start();*/
		return "/TLiveInfo/adminListAjax";
	}


	/**
	 * app端获取app的首页的直播
	*@Author: 90
	*@Description
	*@Param  * @param null
	*@Date 12:02 2018/8/6
	*/
	//@PostMapping(path = "/v1/indexLive")
	@RequestMapping("/v1/indexLive")
	@ResponseBody
	public Object indexLive(@RequestBody Map<String, String> params){
		Map<String,Object> result=new HashMap<String,Object>();
		AppParams appParams=this.getAppParams("/v1/indexLive",params);
		/*if(false==appParams.isSign()){
			return this.returnApp(0,"sign签名错误!",null,null);
		}*/

		//走缓存
		TLiveInfo tLiveInfo=tLiveInfoService.getHotKey();

		if(tLiveInfo!=null){
            Map<String,Integer> onlineNumMap=null;
            if(redisService.exists(RedisKeyConstant.ONLINE_COUNT_KEY)){
                onlineNumMap=(Map<String,Integer>)redisService.get(RedisKeyConstant.ONLINE_COUNT_KEY);
                if(onlineNumMap!=null){
                	if(onlineNumMap.containsKey(tLiveInfo.getId())){
						tLiveInfo.setOnlineNum(onlineNumMap.get(tLiveInfo.getId()));
					}
                }
            }
			result.put("status",1);
			result.put("msg","success");
			result.put("error",null);
			result.put("result",tLiveInfo);

		}else{
			result.put("status",0);
			result.put("msg","没有正在进行的直播！");
			result.put("error",null);
		}

		return result;
	}

	/**
	 * app端获取某个直播的信息
	*@Author: 90
	*@Description
	*@Param  * @param null
	*@Date 10:24 2018/8/8
	*/
	@RequestMapping(path = "/v1/oneLive")
	@ResponseBody
	public Object oneLive(@RequestBody Map<String, String> params){
		System.out.println(params);
		String mobileType=params.get("mobileType");
		String token=params.get("token");
		String ime=params.get("ime");
		String id=params.get("id");
		if(id==null||"".equals(id)){
			return this.returnApp(0,"直播id有误!",null,null);
		}
		Map<String,Object> result=new HashMap<String,Object>();
		if(mobileType!=null&&!"".equals(mobileType)){//说明是从移动端来的数据
			String userSig="";
			boolean isLogin=false;
			AppParams appParams=this.getAppParams("/v1/oneLive",params);
			if(false==appParams.isSign()){
				return this.returnApp(0,"sign签名错误!",null,null);
			}else{//返回数据
				BaseUser user=this.getBaseUser(token);
                if(null!=user.getUserId()&&!"".equals(user.getUserId())&&true==user.isLogin()){
					userSig=tLiveImService.getUserSignCache(user.getUserId());
				}else{
					userSig=tLiveImService.getUserSignCache(ime);
				}
                if((null!=user.getUserId()&&!"".equals(user.getUserId())&&user.isLogin()==false)||"0".equals(user.getStatus())){
                	result.put("msg","用户登录过期！");
                    result.put("msg_type","-1");
                }
				if(true==user.isLogin()){
					isLogin=false;
				}
				//TLiveInfo tLiveInfo=tLiveInfoService.get(id);
				TLiveInfo tLiveInfo=tLiveInfoService.getOneCacheLive(id);
				Map<String,Integer> onlineNumMap=null;
				if(redisService.exists(RedisKeyConstant.ONLINE_COUNT_KEY)){
					onlineNumMap=(Map<String,Integer>)redisService.get(RedisKeyConstant.ONLINE_COUNT_KEY);
				}
				if(onlineNumMap.containsKey(tLiveInfo.getId())){
					tLiveInfo.setOnlineNum(onlineNumMap.get(tLiveInfo.getId()));
				}
				String  hfUrl=tLivePlayUrlService.getUrlListByLiveId(Integer.valueOf(id));
				tLiveInfo.setHfurl(hfUrl);
				result.put("status",0);
				result.put("result",tLiveInfo);
				result.put("isLogin",isLogin);
				result.put("userSig",userSig);
                return result;

			}

		}

		if(null!=id&&!"".equals(id)){
			TLiveInfo tLiveInfo=tLiveInfoService.getOneCacheLive(id);
			if(tLiveInfo!=null){
				//return this.returnApp(1,"success",null,tLiveInfo);
				String  hfUrl=tLivePlayUrlService.getUrlListByLiveId(Integer.valueOf(id));
				tLiveInfo.setHfurl(hfUrl);
				result.put("status",0);
				result.put("result",tLiveInfo);
				result.put("msg","success");
				return result;
			}else{
				return this.returnApp(1,"没有该直播！",null,null);
			}

		}else{
			result.put("status",0);
			result.put("msg","参数传递错误，直播id无效！");
			result.put("error","fail");
		}
		return result;
	}


	/**
	 * app端获取直播的更多列表
	*@Author: 90
	*@Description
	*@Param  * @param null
	*@Date 10:24 2018/8/8
	*/
	@PostMapping(path = "/v1/liveList")
	@ResponseBody
	public Object liveList(@RequestBody Map<String, String> params){
		Map<String,Object> result=new HashMap<String,Object>();
		AppParams appParams=this.getAppParams("/v1/liveList",params);
		System.out.println(appParams.isSign()+"---------------------------------sign");
		if(false==appParams.isSign()){
			this.returnApp(0,"sign签名错误!",null,null);
			return result;
		}
		String pageNoStr=params.get("pageNo");
		int pageNo=1;
		if(null!=pageNoStr&&!"".equals(pageNoStr)){
			pageNo=Integer.valueOf(pageNoStr);
		}
		Map<String,String>map=new HashMap<String,String>();//暂时参数为空
		int pageSize=10;

		Map<String,Integer> onlineNumMap=null;
		if(redisService.exists(RedisKeyConstant.ONLINE_COUNT_KEY)){
			onlineNumMap=(Map<String,Integer>)redisService.get(RedisKeyConstant.ONLINE_COUNT_KEY);
		}
		PageBean<TLiveInfo> pageData=null;
		List<TLiveInfo> dataList=null;
		Integer total=tLiveInfoService.getHotListTotal();
		if(1==pageNo){
			if(tLiveInfoService.getHotListKey(1)!=null){
				dataList=tLiveInfoService.getHotListKey(1);
			}

		}else{
			pageData=tLiveInfoService.getAppLiveList(map,pageNo,pageSize);
			if(pageNo>pageData.getTotalPage()){
				result.put("status",1);
				result.put("total",total);
				result.put("result",new ArrayList());
				result.put("msg","已经到底了。");
				return result;
			}
			dataList= pageData.getItems();
			total=pageData.getTotalNum();
			tLiveInfoService.setHotListTotal(total);
		}

		if(dataList!=null&&dataList.size()>0){//遍历，更新在线人数
			if(onlineNumMap!=null&&onlineNumMap.size()>0){
				List<TLiveInfo> dataListRlt= new LinkedList<>();
				for (int i=0;i<dataList.size();i++){
					TLiveInfo thisInfo=dataList.get(i);
					if(onlineNumMap.containsKey(thisInfo.getId())){
						thisInfo.setOnlineNum(onlineNumMap.get(thisInfo.getId()));
					}

					dataListRlt.add(thisInfo);
				}
				result.put("result",dataListRlt);
			}else{
				result.put("result",dataList);
			}

		}else{
			result.put("result",null);
		}
		result.put("status",1);
		result.put("total",total);
		result.put("msg","success");
		result.put("error",null);

		return result;
	}
	/**
	 * 设置开始或者结束
	 *@Author: 90
	 *@Description
	 *@Param  * @param null
	 *@Date 17:05 2018/8/6
	 */
	@RequestMapping("/v1/setBeginOrEndTime")
	@ResponseBody
	public String setBeginOrEndTime(HttpServletRequest request){
		String flag=request.getParameter("flag");//1是开始，3结束
		String id=request.getParameter("id");

		String rlt="0";//更新失败
		if(id!=null&&!"".equals(id)){
			TLiveInfo tLiveInfo=tLiveInfoService.get(id);
			tLiveInfo.setModTime(new Date());
			if("1".equals(flag)){
				/*String dmId=tLiveImService.createGroupAVChatRoom(id+"_"+"dm");
				String zjId=tLiveImService.createGroupAVChatRoom(id+"_"+"zj","ChatRoom");
				tLiveInfo.setDmGroupId(dmId);
				tLiveInfo.setSpeakerGroupId(zjId);*/
				tLiveInfo.setStatus(1);
				tLiveInfoService.updateBeginOrEnd(tLiveInfo);
				pushMessageService.sendMessageToSubscribeUser(tLiveInfo);
				//推状态改变
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("msg_type", "3");//直播开始
                jsonObject.put("content", tLiveInfo.getId());//id
                tLiveImService.sendMessage(tLiveInfo.getDmGroupId(), jsonObject.toJSONString());

				rlt="1";
			}else if("3".equals(flag)){
				tLiveInfo.setStatus(3);
				tLiveInfo.setEndTime(new Date());
				tLiveInfoService.updateBeginOrEnd(tLiveInfo);
				//结束直播，要清空相应的弹幕id和主讲弹幕id
				tLiveImService.destroyGroup(tLiveInfo.getSpeakerGroupId());
				tLiveImService.destroyGroup(tLiveInfo.getDmGroupId());
				rlt="1";
			}
			tLiveInfoService.setOneCacheLive(tLiveInfo);
			tLiveInfoService.setHotKey(tLiveInfo);//更新内存
			tLiveInfoService.setHotListKey(1);//更新更多列表

		}
		return rlt;
	}


	/**
	 * 删除直播
	*@Author: 90
	*@Description
	*@Param  * @param null
	*@Date 19:39 2018/8/6
	*/
	@RequestMapping("/v1/deleteLive")
	@ResponseBody
	public String deleteLive(HttpServletRequest request){
		String id=request.getParameter("id");
		String rlt="0";//更新失败
		if(id!=null&&!"".equals(id)){
			TLiveInfo tLiveInfo=tLiveInfoService.get(id);
			tLiveInfoService.deleteLive(Integer.valueOf(id));
			tLiveInfoService.delOneCacheLive(id);
			tLiveInfoService.adminSetHotKey();
			tLiveInfoService.setHotListKey(1);//更新更多列表
			//删除直播，要清空相应的弹幕id和主讲弹幕id
			tLiveImService.destroyGroup(tLiveInfo.getSpeakerGroupId());
			tLiveImService.destroyGroup(tLiveInfo.getDmGroupId());
			rlt="1";
		}
		return rlt;
	}





	/**
     * 图片上传
	*@Author: 90
	*@Description
	*@Param
	*@Date 11:18 2018/8/13
	*/
	@PostMapping("/v1/uploadV2")
	@ResponseBody
	public Map<String,String> uploadImgV2(@RequestParam("imgFile") MultipartFile multipartFile,HttpServletRequest request)  {
		Map<String,String> rltMap=new HashMap<String,String>();
		rltMap.put("status","0");
		String uploadUrl=this.getUserInfoUrl()+"/live/";//"http://shiliucaijing.baike.com/api/v1/live/";
		String uploadId=request.getParameter("uploadId");
		String url=uploadUrl+uploadId;
		String root_fileName = multipartFile.getOriginalFilename();
		if(multipartFile.getSize()>0){

			String dayStr=DateUtil.fullDateToMinutes();
			Date date;
			try {
				date=DateUtil.parseFullDateToMinutes(dayStr);
				String timeStr=date.getTime()+"";
				try {
					String token=MD5Util.encode2MD5(uploadId)+"hdbk"+timeStr.substring(0,timeStr.length()-3);
					token=MD5Util.encode2MD5(token);

					try {
						String rlt=HttpFileUploadUtil.uploadIconPic(url,token,multipartFile.getBytes(),root_fileName);
						if(rlt!=null&&!rlt.equals("")){
							JSONObject jsonImg = JSONObject.parseObject(rlt);
							String status=jsonImg.getString("status");
							if("1".equals(status)){
								rltMap.put("status","1");//上传成功
								Map<String,String> thisMap=(Map<String,String>)jsonImg.get("result");
								rltMap.put("imgUrl",thisMap.get("url"));
								//rltMap.put("imgUrl","http://newsfeed.att.hudong.com/live/96efe8e2e87c13b0ca4baf3d5772e3f1.jpg");
							}

						}
					} catch (IOException e) {
						logger.info("上传图片失败=========================================");
						e.printStackTrace();
					}

				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}


			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else {
			//throw new Exception("上传失败：上传文件不能为空");
		}
		return rltMap;
	}


	/**
	 * 导出excel功能
	 * @param request
	 * @return
	 */
	@RequestMapping("/v1/exportLive")
	public String  exportLive(HttpServletRequest request,HttpServletResponse response){
		String title=request.getParameter("title");
		String id=request.getParameter("id");
		String status=request.getParameter("status");
		String endTime=request.getParameter("endTime");
		String startTime=request.getParameter("startTime");
		String pageNoStr=request.getParameter("pageNo");
		int pageNo=1;
		if(null!=pageNoStr&&!"".equals(pageNoStr)){
			pageNo=Integer.valueOf(pageNoStr);
		}
		int pageSize=1000000;
		Map<String,String>map=new HashMap<String,String>();
		map.put("startTime",startTime);
		map.put("endTime",endTime);
		map.put("status",status);
		map.put("title",title);
		map.put("id",id);
		PageBean<TLiveInfo> pageData=tLiveInfoService.getAdminListPageV2(map,pageNo,pageSize);
		List<TLiveInfo> dataList=pageData.getItems();
		try {
			String fileName="直播列表"+DateUtil.fullDateToMinutes();
			BufferedOutputStream bos = null;
			String userAgent = request.getHeader("user-agent");
			if (userAgent != null && userAgent.indexOf("Firefox") >= 0 || userAgent.indexOf("Chrome") >= 0 || userAgent.indexOf("Safari") >= 0) {
				fileName = new String((fileName).getBytes(), "ISO8859-1");
			} else {
				fileName = URLEncoder.encode(fileName, "UTF8"); // 其他浏览器
			}
			try {
				response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xlsx");
				OutputStream out =response.getOutputStream();
				String[] headers={"直播ID","直播名称","直播开始时间","直播状态","可否回放","操作人","操作时间"};
				String[] columns=null;
				ExcelUtil.expoortExcelx("石榴财经直播",headers,columns,dataList,out);
			}finally {
				if (bos != null) {
					try {
						bos.close();
					} catch (IOException e) {
						throw e;
					}
				}
			}


		} catch (Exception e) {
			e.printStackTrace();
		}









		return null;
	}


}
