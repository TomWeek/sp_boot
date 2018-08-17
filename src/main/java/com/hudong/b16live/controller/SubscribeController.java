package com.hudong.b16live.controller;

import com.hudong.b16live.bean.BaseUser;
import com.hudong.b16live.bean.TLiveUserSubscribe;
import com.hudong.b16live.controller.validator.IsSubscribeValidator;
import com.hudong.b16live.controller.validator.SubscribeSubmitValidator;
import com.hudong.b16live.service.TLiveUserSubscribeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import java.util.*;

/**
 * @description：订阅相关处理
 * @author：ZJW
 * @date：2018/08/06
 */
@Controller
@RequestMapping("/subscribe")
public class SubscribeController extends BaseController {


    /**
     * 日志打印
     */
    private static final Logger logger = Logger.getLogger(SubscribeController.class);

    @Autowired
    private TLiveUserSubscribeService tLiveUserSubscribeService;

    /**
     * 预约相关参数
     * type 0:预约 1：取消预约
     * @return
     */
    @RequestMapping("/submit")
    @ResponseBody
    public Object subscribe(@RequestBody Map<String, String> params) {

        Map<String,Object> resultMap = new HashMap<>(2);
        resultMap.put("status",0);
        SubscribeSubmitValidator actionForm = new SubscribeSubmitValidator(params);
        {
            Set<ConstraintViolation<SubscribeSubmitValidator>> constraintViolations = validator.validate(actionForm);
            // 如果验证结果有错误发生,构造 操作失败的returnType
            if (constraintViolations.size() > 0) {
                StringBuilder sb = new StringBuilder();
                for (Iterator<ConstraintViolation<SubscribeSubmitValidator>> it = constraintViolations.iterator(); it.hasNext(); ) {
                    sb.append(it.next().getMessage());
                    if (it.hasNext()) {
                        sb.append("; ");
                    }
                }
                resultMap.put("msg",sb.toString());
                return renderSuccess( resultMap );
            }
        }
        BaseUser user=this.getBaseUser(actionForm.getToken());
        if(user==null||"0".equals(user.getStatus())){
            resultMap.put("msg","用户未登录");
            return renderSuccess( resultMap );
        }
        int liveId=Integer.parseInt(actionForm.getLiveId());
        TLiveUserSubscribe tLiveUserSubscribeOLd=new TLiveUserSubscribe();
        tLiveUserSubscribeOLd.setUserId(user.getUserId());
        tLiveUserSubscribeOLd.setLiveId(liveId);
        tLiveUserSubscribeOLd=tLiveUserSubscribeService.getTLiveUserSubscribe(tLiveUserSubscribeOLd);
        if(tLiveUserSubscribeOLd!=null&&tLiveUserSubscribeOLd.getId()!=null){
            tLiveUserSubscribeOLd.setDelFlag(Integer.parseInt(actionForm.getType()));
            tLiveUserSubscribeService.updateTLiveUserSubscribe(tLiveUserSubscribeOLd);
        }else{
            TLiveUserSubscribe tLiveUserSubscribe=new TLiveUserSubscribe();
            tLiveUserSubscribe.setUserId(user.getUserId());
            tLiveUserSubscribe.setUserName(user.getNickName());
            tLiveUserSubscribe.setCreateTime(new Date());
            tLiveUserSubscribe.setLiveId(liveId);
            tLiveUserSubscribe.setDelFlag(Integer.parseInt(actionForm.getType()));
            tLiveUserSubscribe.setPushStatus(1);
            tLiveUserSubscribeService.saveTLiveUserSubscribe(tLiveUserSubscribe);
        }
        resultMap.put("status",1);
        return renderSuccess( resultMap );
    }

    /**
     * 判断是否订阅：1订阅0未订阅
     * @return
     */
    @RequestMapping("/isSubscribe")
    @ResponseBody
    public Object isSubscribe(@RequestBody Map<String, String> params) {

        Map<String,Object> resultMap = new HashMap<>(2);
        resultMap.put("status","0");
        IsSubscribeValidator actionForm = new IsSubscribeValidator(params);
        {
            Set<ConstraintViolation<IsSubscribeValidator>> constraintViolations = validator.validate(actionForm);
            // 如果验证结果有错误发生,构造 操作失败的returnType
            if (constraintViolations.size() > 0) {
                StringBuilder sb = new StringBuilder();
                for (Iterator<ConstraintViolation<IsSubscribeValidator>> it = constraintViolations.iterator(); it.hasNext(); ) {
                    sb.append(it.next().getMessage());
                    if (it.hasNext()) {
                        sb.append("; ");
                    }
                }
                resultMap.put("msg",sb.toString());
                return renderSuccess( resultMap );
            }
        }
        BaseUser user=this.getBaseUser(actionForm.getToken());
        if(user==null||"0".equals(user.getStatus())){
            resultMap.put("msg","用户未登录");
            return renderSuccess( resultMap );
        }
        String userId=user.getUserId();
        int liveId=Integer.parseInt(actionForm.getLiveId());
        TLiveUserSubscribe tLiveUserSubscribe=new TLiveUserSubscribe();
        tLiveUserSubscribe.setUserId(userId);
        tLiveUserSubscribe.setLiveId(liveId);
        tLiveUserSubscribe=tLiveUserSubscribeService.getTLiveUserSubscribe(tLiveUserSubscribe);
        if(tLiveUserSubscribe!=null&&0==tLiveUserSubscribe.getDelFlag()){
            resultMap.put("status","1");
        }
        return renderSuccess( resultMap );
    }
}
