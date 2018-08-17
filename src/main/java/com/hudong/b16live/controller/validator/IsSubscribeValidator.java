package com.hudong.b16live.controller.validator;

import com.hudong.b16live.validate.LegalRequest;
import com.hudong.b16live.validate.LegalRequestValidatable;
import org.hibernate.validator.constraints.NotBlank;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author zhangjianwei
 * @title
 * @Description: <br>
 * <br>
 * @Company: 在线
 * @Created on 2018/8/8.
 */
@LegalRequest
public class IsSubscribeValidator implements LegalRequestValidatable {
    @NotBlank(message = "用户Token不能为空")
    private  String  token;
    @NotBlank(message = "直播ID不能为空")
    private  String liveId;
    @NotBlank(message = "sign不能为空")
    private String sign;

    public IsSubscribeValidator(Map<String, String> params) {
         token=params.get("token");
         liveId=params.get("liveId");
         sign=params.get("sign");
    }


    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String getSourceString() {
        StringBuffer sb = new StringBuffer();
        sb.append("http://b16live.baike.com/subscribe/isSubscribe.do")
                .append("?token=").append(this.getToken())
                .append("&liveId=").append(this.getLiveId());
        return sb.toString();
    }

    @Override
    public String getSign() {
        return sign;
    }

    @Override
    public String getPrivateKey() {
        return "@baike&hudong*";
    }
}
