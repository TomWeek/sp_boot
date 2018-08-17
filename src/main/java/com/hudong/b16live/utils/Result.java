package com.hudong.b16live.utils;

import java.io.Serializable;

/**
 * @description：操作结果集
 * @author：JBL
 * @date：2015/10/1 14:51
 */
public class Result implements Serializable {

    private static final long serialVersionUID = 5576237395711742681L;

    private String success = "0";

    private String msg = "";

    private Object obj = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "{" +
                "'success':" + success +
                ", 'msg':'" + msg + '\'' +
                ", 'obj':" + obj +
                '}';
    }
}
