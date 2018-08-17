package com.hudong.b16live.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.hudong.b16live.utils.HttpClientUtil;
import com.hudong.b16live.utils.LiveUtils;
import com.hudong.b16live.utils.MD5Util;
import com.hudong.b16live.utils.Sigature;
import com.hudong.b16live.utils.SignConstant;
import com.hudong.core.common.lang.utils.DigestUtils;
import com.hudong.core.common.logger.HuDongErrorCodeEnum;
import com.hudong.core.common.logger.HuDongLogger;
import com.hudong.core.common.logger.HuDongLoggerFactory;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yimi099
 * @Title: TLiveImService
 * @Copyright: Copyright (c) 2005
 * @Description:与腾讯im交互
 * @Company: 在线
 * @Created on 2018/8/7 11:53
 */
@Service
public class TLiveImService {

    private final static HuDongLogger logger = HuDongLoggerFactory.getLogger(TLiveImService.class);

    private String USER_URL = "";

    @Autowired
    private RedisService redisService;

    /**
     * FIXME 用户是否存在 ,暂时接口没有提供，全部返回true
     *
     * @return
     */
    public boolean isExistUser(String userIden) {
        Map<String, String> params = new HashMap<>();
        params.put("userIden", userIden);
        String result = HttpClientUtil.httpPost(USER_URL, params);
        return true;
    }


    public String createGroupAVChatRoom(String programId, String type) {
        String param = getRequestParam("");
        if (StringUtils.isBlank(param) || StringUtils.isBlank(programId)) {
            return "";
        }
        String url = SignConstant.CREATEGROUPURL + param;
        String currentTime = LiveUtils.getTimeStamp();
        try {
            JSONObject json = new JSONObject();
            json.put("Owner_Account", SignConstant.IDENTIFIER);
            json.put("Type", type);
            json.put("GroupId", programId + "_" + currentTime + "_" + type);
            json.put("Name", type + currentTime);

            String resultStr = HttpClientUtil.sendPostJson(url, json.toString());
            if (StringUtils.isBlank(resultStr)) {
                logger.info("createGroup"+ type +"_{}_Tencent_return null！", new Object[]{programId});
                return "";
            }
            logger.info("createGroupAVChatRoom_{}_Tencent_return_{}", new Object[]{programId, resultStr});
            JSONObject result = JSONObject.parseObject(resultStr);
            if (result != null && result.containsKey("ActionStatus")) {
                if ("ok".equalsIgnoreCase(result.getString("ActionStatus")) && result.containsKey("GroupId")) {
                    logger.info("createGroupAVChatRoom_{}_Tencent_return_groupID_{}", new Object[]{programId, result.getString("GroupId")});
                    return result.getString("GroupId");
                }
            }
        } catch (JSONException e) {
            logger.info("createGroupAVChatRoom_{}_Tencent_return_json_error!", new Object[]{programId});
            logger.error(HuDongErrorCodeEnum.ProgramError, "createGroupAVChatRoom", e);
        }
        logger.info("createGroupAVChatRoom_{}_Tencent_return_groupID_null", new Object[]{programId});
        return "";
    }

    /**
     * 创建弹幕聊天室
     *
     * @param programId
     * @return
     */
    public String createGroupAVChatRoom(String programId) {
        return createGroupAVChatRoom(programId, "AVChatRoom");
    }

    /**
     * 发送群消息
     *
     * @param groupId
     * @param jsonText：格式的字符串
     * @return
     */
    public boolean sendMessage(String groupId, String jsonText) {
      return  sendMessage(groupId,jsonText,SignConstant.IDENTIFIER);
    }
    /**
     * 发送群消息
     *
     * @param groupId
     * @param jsonText：格式的字符串
     * @return
     */
    public boolean sendMessage(String groupId, String jsonText,String userNo) {
        String param = getRequestParam("");
        if (StringUtils.isBlank(param) || StringUtils.isBlank(groupId)) {
            return false;
        }
        String url = SignConstant.SENDGROUPMSGURL + param;
        try {
            JSONObject json = new JSONObject();
            json.put("GroupId", groupId);
            json.put("From_Account", userNo);
            json.put("Random", LiveUtils.getRandom());

            JSONArray msgBody = new JSONArray();
            JSONObject msg = new JSONObject();
            msg.put("MsgType", "TIMTextElem");
            // 封装消息内容
            JSONObject msgContent = new JSONObject();
            msgContent.put("Text", jsonText);
            msg.put("MsgContent", msgContent);
            msgBody.add(msg);
            json.put("MsgBody", msgBody);

            String resultStr = HttpClientUtil.sendPostJson(url, json.toString());
            if (StringUtils.isBlank(resultStr)) {
                return false;
            }
            JSONObject result = JSONObject.parseObject(resultStr);
            if (result != null && result.containsKey("ActionStatus") && "ok".equalsIgnoreCase(result.getString("ActionStatus"))) {
                return true;
            }
        } catch (Exception e) {
            logger.error(HuDongErrorCodeEnum.ProgramError, "sendMessage", e);
        }
        return false;
    }


    /**
     * 获取在线人数
     *
     * @param groupId
     * @return
     */
    public int getMemberNumber(String groupId) {
        String param = getRequestParam("");
        if (StringUtils.isBlank(param) || StringUtils.isBlank(groupId)) {
            return 0;
        }
        String url = SignConstant.GETGROUPINFOURL + param;
        try {
            JSONObject json = new JSONObject();
            // 封装组信息
            List<String> groupIdList = new ArrayList<>();
            groupIdList.add(groupId);
            json.put("GroupIdList", groupIdList);

            // 封装获取成员人数信息
            JSONObject groupBaseInfoFilter = new JSONObject();
            List<String> filterList = new ArrayList<>();
            filterList.add("MemberNum");
            groupBaseInfoFilter.put("GroupBaseInfoFilter", filterList);
            json.put("ResponseFilter", groupBaseInfoFilter);

            String resultStr = HttpClientUtil.sendPostJson(url, json.toString());
            if (StringUtils.isBlank(resultStr)) {
                return 0;
            }
            JSONObject result = JSONObject.parseObject(resultStr);
            if (result != null && result.containsKey("ActionStatus") && "ok".equalsIgnoreCase(result.getString("ActionStatus")) && result.containsKey("GroupInfo")) {
                JSONArray groupInfoList = result.getJSONArray("GroupInfo");
                if (groupInfoList != null && groupInfoList.size() > 0) {
                    JSONObject groupInfo = (JSONObject) groupInfoList.get(0);
                    if (groupInfo.containsKey("MemberNum")) {
                        return groupInfo.getIntValue("MemberNum");
                    }
                }
            }
        } catch (Exception e) {
            logger.error(HuDongErrorCodeEnum.ProgramError, "getMemberNumber", e);
        }
        return 0;
    }

    /**
     * 解散群组接口
     *
     * @param groupId
     * @return
     */
    public boolean destroyGroup(String groupId) {
        String param = getRequestParam("");
        if (StringUtils.isBlank(param) || StringUtils.isBlank(groupId)) {
            return false;
        }
        String url = SignConstant.DESTROYGROUPURL + param;
        try {
            JSONObject json = new JSONObject();
            json.put("GroupId", groupId);

            String resultStr = HttpClientUtil.sendPostJson(url, json.toString());
            if (StringUtils.isBlank(resultStr)) {
                return false;
            }
            JSONObject result = JSONObject.parseObject(resultStr);
            if (result != null && result.containsKey("ActionStatus") && "ok".equalsIgnoreCase(result.getString("ActionStatus"))) {
                return true;
            }
        } catch (Exception e) {
            logger.error(HuDongErrorCodeEnum.ProgramError, "destroyGroup", e);
        }
        return false;
    }
    
    //调用腾讯云视频拼接接口
    public String concatVideo(String file1,String file2) {
    	StringBuffer param = new StringBuffer();
    	long currentTime = new Date().getTime()/1000;
        int randomInt = LiveUtils.getRandom();
    	param.append("&srcFileList.0.fileId=").append(file1);
        param.append("&srcFileList.1.fileId=").append(file2);
        param.append("&dstType.0=").append("mp4");
        param.append("&name=").append("b16live"+currentTime+randomInt);
        param.append("&Region=").append("ap-beijing");
        param.append("&Timestamp=").append(currentTime);
        param.append("&Nonce=").append(randomInt);
        param.append("&SecretId=").append(SignConstant.TENCENTSECRETID);
        param.append("&SignatureMethod=").append("HmacSHA256");//签名算法Signature
        String url = SignConstant.CONCATVIDEO + param.toString();
        String signature = MD5Util.hmacSHA256(SignConstant.TENCENTSECRETKEY, url);
        try {
			signature = URLEncoder.encode(signature,"UTF-8");
            url += "&Signature=" + signature;
            logger.info("调用腾讯云地址："+url);
            //JSONObject json = new JSONObject();
            //json.put("Signature", signature);
            String resultStr = HttpClientUtil.sendPostJson(url, "");
            if (StringUtils.isBlank(resultStr)) {
            	logger.error(HuDongErrorCodeEnum.RemoteRequestError, "调用腾讯云视频拼接接口失败,返回参数为空");
                return "";
            }
            JSONObject result = JSONObject.parseObject(resultStr);
            if (result != null && result.containsKey("code")) {
            	String code = result.getString("code");
            	String vodTaskId = result.getString("vodTaskId");
            	if ("0".equals(code)) {//拼接成功
            		return vodTaskId;
				}else {
					logger.error(HuDongErrorCodeEnum.RemoteRequestError, "调用腾讯云视频拼接接口失败，"+result.toString());
				}
            }
        } catch (JSONException e) {
            logger.error(HuDongErrorCodeEnum.ProgramError, "createGroupAVChatRoom", e);
        }catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        return "";
    }

    /**
     * 获取用户sign
     *
     * @param userIden
     * @return
     */
    public String getUserSignCache(String userIden) {
        String userKey = DigestUtils.digest(SignConstant.HDLIVEUSERSIGN + userIden);
        String sign = (String) redisService.get(userKey);
        if (StringUtils.isBlank(sign)) {
            redisService.set(userKey, Sigature.getSign(userIden), SignConstant.HDLIVECACHETIME);
            sign = (String) redisService.get(userKey);
        }
        return sign;
    }


    private String getHdSignCache() {
        // FIXME:扔到常量类
//        String key = DigestUtils.digest(SignConstant.HDLIVESIGN);
//        String sign = (String) redisService.get(key);
//        if (StringUtils.isBlank(sign)) {
//            synchronized (this) {
//                redisService.set(key,"");
//                if (StringUtils.isBlank((String) redisService.get(key))) {
//                    redisService.set(key, Sigature.getHdSign(), SignConstant.HDLIVECACHETIME);
//                }
//                sign = (String) redisService.get(key);
//            }
//        }
        //return sign;
        return SignConstant.SIGN;

    }

    /**
     * 拼接请求参数
     *
     * @return
     */
    private String getRequestParam(String sign) {
        StringBuffer param = new StringBuffer();
        if (StringUtils.isBlank(sign)) {
            //获取签名
            String usersig = getHdSignCache();
            if (StringUtils.isBlank(usersig)) {
                return "";
            }
            param.append("usersig=").append(usersig);
        } else {
            param.append("usersig=").append(sign);
        }
        param.append("&identifier=").append(SignConstant.IDENTIFIER);
        param.append("&sdkappid=").append(SignConstant.SKDAPPID);
        param.append("&random=").append(LiveUtils.getRandom());
        param.append("&contenttype=json");
        return param.toString();
    }
    public static void main(String[] args) {
    	//concatVideo("5285890781113255782","5285890781111314728");
    	//System.out.println(new Date().getTime()/1000);
	}
}
