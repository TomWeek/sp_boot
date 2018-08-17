package com.hudong.b16live.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hudong.b16live.bean.BaseUser;
import com.hudong.b16live.bean.TLiveInfo;
import com.hudong.b16live.bean.TLiveSpeaker;
import com.hudong.b16live.bean.TLiveUserSubscribe;
import com.hudong.b16live.controller.validator.SpeakerListValidator;
import com.hudong.b16live.service.TLiveImService;
import com.hudong.b16live.service.TLiveInfoService;
import com.hudong.b16live.service.TLiveSpeakerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.security.ssl.SSLContextImpl;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @description：消息处理
 * @author：JBL
 * @date：2017/12/12
 */
@Controller
@RequestMapping("/imMessage")
public class SpeakerController extends BaseController {

    /**
     * 日志打印
     */
    private static final Logger logger = Logger.getLogger(SpeakerController.class);

    @Autowired
    private TLiveInfoService tLiveInfoService;

    @Autowired
    private TLiveSpeakerService tLiveSpeakerService;
    @Autowired
    private TLiveImService tLiveImService;

    @RequestMapping("/submit")
    @ResponseBody
    public Object message(HttpServletRequest request) throws UnsupportedEncodingException {


        Map<String,Object> resultMap = new HashMap<>(2);
        resultMap.put("status","0");
        String userNo=request.getParameter("userNo");
        String liveId=request.getParameter("liveId");
        String content=request.getParameter("content");
        String userNick=request.getParameter("userNick");
        TLiveSpeaker tLiveSpeaker=new TLiveSpeaker();
        tLiveSpeaker.setAccount(userNo);
        tLiveSpeaker.setLiveId(Integer.parseInt(liveId));
        tLiveSpeaker.setCreateTime(new Date());
        tLiveSpeaker.setMsg(content);
        tLiveSpeaker.setDelFlag(0);
        TLiveInfo tLiveInfo=tLiveInfoService.getOneCacheLive(liveId);
        JSONObject jsonText=new JSONObject();
        jsonText.put("nick", userNick);
        jsonText.put("content",content);
        jsonText.put("msg_type","2");
        logger.info("开始发送主讲人信息");
        boolean sendResult=tLiveImService.sendMessage(tLiveInfo.getSpeakerGroupId(),jsonText.toJSONString(),userNo);
        logger.info("发送主讲人信息结果"+sendResult);
        tLiveSpeakerService.saveTLiveSpeaker(tLiveSpeaker);
        resultMap.put("status","1");
        return renderSuccess( resultMap );
    }
    @RequestMapping("/sendPage")
    public Object sendPage(Model model) {
        List<TLiveInfo> allLive=tLiveInfoService.getAllLive();
        List<Map<String,String>> liveList=new ArrayList<Map<String,String>>();
        if(allLive!=null&&allLive.size()>0){
            for(TLiveInfo liveInfo:allLive){
                if(1==liveInfo.getStatus()||2==liveInfo.getStatus()){
                    Map <String,String> tempMap=new HashMap<String,String>();
                    tempMap.put("liveId",liveInfo.getId()+"");
                    tempMap.put("liveTitle",liveInfo.getTitle());
                    liveList.add(tempMap);
                }
            }
        }
        model.addAttribute( "liveList", liveList );
        return "sendMessage";
    }
    @RequestMapping("/list")
    @ResponseBody
    public Object list(@RequestBody Map<String, String> params) throws UnsupportedEncodingException {


        Map<String,Object> resultMap = new HashMap<>(2);
        resultMap.put("status",0);
        SpeakerListValidator actionForm = new SpeakerListValidator(params);
        {
            Set<ConstraintViolation<SpeakerListValidator>> constraintViolations = validator.validate(actionForm);
            // 如果验证结果有错误发生,构造 操作失败的returnType
            if (constraintViolations.size() > 0) {
                StringBuilder sb = new StringBuilder();
                for (Iterator<ConstraintViolation<SpeakerListValidator>> it = constraintViolations.iterator(); it.hasNext(); ) {
                    sb.append(it.next().getMessage());
                    if (it.hasNext()) {
                        sb.append("; ");
                    }
                }
                resultMap.put("msg",sb.toString());
                return renderSuccess( resultMap );
            }
        }
        if(actionForm.getLiveId()==null){
            resultMap.put("msg","直播ID不能为空");
            return renderSuccess( resultMap );
        }
        int liveId=Integer.parseInt(actionForm.getLiveId());
        List<TLiveSpeaker> list=tLiveSpeakerService.findSpeakerRecordByLiveId(liveId);
        JSONArray resultData=new JSONArray();
        if(list!=null&&list.size()>0){
            for(TLiveSpeaker ts:list){
                JSONObject obj=new JSONObject();
                obj.put("id",ts.getId());
                obj.put("msg",ts.getMsg());
                obj.put("account",ts.getAccount());
                obj.put("createTime",ts.getCreateTime());
                if("shiliuadmin1".equals(ts.getAccount())){
                   obj.put("name","石榴君");
                }else if("shiliuadmin2".equals(ts.getAccount())){
                    obj.put("name","石榴姐");
                }
                resultData.add(obj);
            }
        }
        resultMap.put("data",resultData);
        resultMap.put("status",1);
        return renderSuccess( resultMap );
    }
}
