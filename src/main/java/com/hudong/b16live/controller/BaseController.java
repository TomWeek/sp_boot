package com.hudong.b16live.controller;


import com.alibaba.fastjson.JSONObject;
import com.hudong.b16live.bean.AppParams;
import com.hudong.b16live.bean.BaseUser;
import com.hudong.b16live.utils.*;
import com.hudong.core.common.logger.HuDongLogger;
import com.hudong.core.common.logger.HuDongLoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @description：基础 controller
 * @author：JBL
 * @date：2015/10/1 14:51
 */
public abstract class BaseController {

    private final static HuDongLogger logger = HuDongLoggerFactory.getLogger(BaseController.class);

    //调用用户信息的接口
    @Value("${pro.uploadUrl}")
    private  String userInfoUrl;

    //app端接口调用该项目时候的域名
    @Value("${pro.domainUrl}")
    public  String domainUrl;
    protected Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {
         /*自动转换日期类型的字段格式*/
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
    }

    public String getUserInfoUrl() {
        return userInfoUrl;
    }

    public void setUserInfoUrl(String userInfoUrl) {
        this.userInfoUrl = userInfoUrl;
    }

    /**
     * ajax失败
     * @param msg 失败的消息
     * @return {Object}
     */
    public Object renderError(String msg) {
        Result result = new Result();
        result.setMsg(msg);
        return result;
    }

    /**
     * ajax成功
     * @return {Object}
     */
    public Object renderSuccess() {
        Result result = new Result();
        result.setSuccess("1");
        return result;
    }

    /**
     * ajax成功
     * @param msg 消息
     * @return {Object}
     */
    public Object renderSuccess(String msg) {
        Result result = new Result();
        result.setSuccess("1");
        result.setMsg(msg);
        return result;
    }

    /**
     * ajax成功
     * @param obj 成功时的对象
     * @return {Object}
     */
    public Object renderSuccess(Object obj) {
        Result result = new Result();
        result.setSuccess("1");
        result.setMsg("请求成功!");
        result.setObj(obj);
        return result;
    }
    /**
     * 远程调用用户信息（从PHP端调用）
     * @param token
     * @return
     */
    public BaseUser getBaseUser(String token) {
        BaseUser user=new BaseUser();
        user.setLogin(false);
        user.setStatus("0");
        String token_md5= null;
        String dayStr=DateUtil.fullDateToMinutes();
        Date date;
        try {
            date=DateUtil.parseFullDateToMinutes(dayStr);
            try {
                token_md5 = MD5Util.encode2MD5(token);
                String timeStr=date.getTime()+"";
                timeStr=timeStr.substring(0,timeStr.length()-3);
                String sign=token_md5+"hdbk"+timeStr;//DateUtils.getDate("yyyy-MM-dd HH:mm");
                sign=MD5Util.encode2MD5(sign);
                String userStr = HttpClientUtil.httpGet(userInfoUrl+"/check"+"?token_md5="+ token_md5+"&sign="+sign+"&sys_time="+timeStr , null);
                logger.info("用户接口请求url:{},token:{},userStr:{}",new Object[]{userInfoUrl+"/check"+"?token_md5="+ token_md5+"&sign="+sign+"&sys_time="+timeStr , token,userStr});
                if(userStr!=null&&!"".equals(userStr)){
                    System.out.println("用户接口:"+userStr);
                    JSONObject jsonUser = JSONObject.parseObject(userStr);
                    //System.out.println(jsonUser+"=====================================================");
                    String status=jsonUser.getString("status");
                    user.setMsg(jsonUser.getString("msg"));
                      if("1".equals(status)){
                        user.setLogin(true);
                        Map<String,String> rltMap=(Map<String,String>)jsonUser.get("result");
                        user.setStatus(1+"");
                        user.setUserId(rltMap.get("id"));
                        user.setNickName(rltMap.get("nick"));
                    }else{
                        user.setStatus(0+"");
                    }
                }

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return user;
    }

    public String[] getParamsOrder(String urlType){
        if("/v1/indexLive".equals(urlType)){
            return new String[]{"token","mobileType", "accessIp", "ime", "sourceId"};
        }else if("/v1/liveList".equals(urlType)){
            return new String[]{"token","mobileType", "accessIp", "ime", "sourceId","pageNo"};
        }else if("/v1/onLineNum".equals(urlType)||"/v1/oneLive".equals(urlType)){
            return new String[]{"token","mobileType", "accessIp", "ime", "sourceId","id"};
        }else{
            return null;
        }

    }
    public AppParams getAppParams(String url, Map<String, String> params){
        AppParams appParams=new AppParams();
        appParams.setSign(false);
        String privateKey="@baike&hudong*";
        //ime  sourceId,mobileType ,accessIp,privateKey
        String mobileType=params.get("mobileType");
        String sourceId=params.get("sourceId");
        String token=params.get("token");
        String sign=params.get("sign");
        //url+?+sourceId=sourceId&
        String[] str=this.getParamsOrder(url);
        String subStr="";
        for (int i=0;i<str.length;i++){
            if(i==0){
                subStr+=str[i]+"="+params.get(str[i]);
            }else{
                subStr+="&"+str[i]+"="+params.get(str[i]);
            }

        }
        url=domainUrl+url+"?";
        url+=subStr+"&privateKey="+privateKey;
        String md5Sign= "";
        try {
            md5Sign = MD5Util.encode2MD5(url);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if(md5Sign.equals(sign)){
            appParams.setSign(true);
        }
        appParams.setToken(params.get("token"));
       return  appParams;
    }

    Map<String,Object> returnApp(Object status,String msg,Object error,Object result){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("status",status);
        map.put("msg",msg);
        map.put("error",error);
        if(result!=null){
            map.put("result",result);
        }
        return map;
    }
}
