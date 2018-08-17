package com.hudong.b16live.bean;

/**
 * AppParams
 *
 * @Title: AppParams.java
 * @Copyright: Copyright (c) 2005
 * @Description:
 * @Company: 百科
 * @Created on 2018/8/10 10:01
 * @Author 90
 */
public class AppParams {


    /**
     * 正确sign是true 否则false
     */
    private boolean isSign;
    private String token;
    public boolean isSign() {
        return isSign;
    }

    public void setSign(boolean sign) {
        isSign = sign;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }




}
