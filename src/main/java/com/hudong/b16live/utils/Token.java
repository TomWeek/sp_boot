package com.hudong.b16live.utils;

/**
 * @author zhangjianwei
 * @title
 * @Description: <br>
 * <br>
 * @Company: 在线
 * @Created on 2018/3/28.
 */
public class Token{

    private long addTime;

    private String ticket;

    private long expiresIn;

    private String token;

    public long getAddTime() {
        return addTime;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public String getTicket() {
        return ticket;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}