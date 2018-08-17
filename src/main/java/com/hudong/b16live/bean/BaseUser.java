package com.hudong.b16live.bean;

import com.alibaba.fastjson.JSONObject;
import com.hudong.b16live.utils.HttpClientUtil;

/**
 * BaseUser
 *
 * @Title: BaseUser.java
 * @Copyright: Copyright (c) 2005
 * @Description:
 * @Company: 百科
 * @Created on 2018/8/7 16:37
 * @Author 90
 */
public class BaseUser {

    /**
     * 登录状态   1:成功  0  失败  -1 token过期
     */
    private String  status;
    private String  nickName;
    private String  userId;
    private boolean isLogin;
    private String  msg;//php端传来调用信息

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

}
