package com.hudong.b16live.validate;


/**
 * 非法请求验证的目标对象接口
 * @Title: LegalRequestValidatable.java
 * @Copyright: Copyright (c) 2005
 * @Description: <br>
 *               <br>
 * @Company: 在线
 * @Created on 2012-7-18 下午07:58:53
 * @author zhaoc
 */
public interface LegalRequestValidatable {

    /**
     * 获得验证源字符串
     * @return
     */
    public String getSourceString();
    
    /**
     * 获得客户端认证字符串
     * @return
     */
    public String getSign();
    
    /**
     * 获取当前接口的自定义安全密钥,如果采用默认，请返回null即可
     * 
     * 该接口可以支持根据不同的类型动态查询密钥，比如不同的用户不同的密钥
     * @return
     */
    public String getPrivateKey();
}
