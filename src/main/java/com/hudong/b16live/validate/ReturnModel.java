package com.hudong.b16live.validate;


/**
 * 接口的返回值对象 存储需要返回的通用参数 状态吗 消息 和具体的业务返回值value 转换成json格式后返回给客户端
 * 
 * @Title: Response.java
 * @Description: <br>
 * <br>
 * @Company: 在线
 * @Created on 2015-10-13 13:30:38O
 * @author zwx
 * @version $Revision: 1.0 $
 * @since 1.0
 */
public class ReturnModel<T> {

    /**
     * status状态码
     * 
     */
    public enum Status {

        /**
         * 成功
         */
        SUCCESS(1),

        /**
         * 失败
         */
        FAIL(0);

        private final int value;

        private Status(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 1成功，0失败
     */
    private int status;

    /**
     * 错误消息内容
     */
    private String msg;

    /**
     * 具体的业务返回值对象
     */
    private T value;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
