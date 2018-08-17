package com.hudong.b16live.validate;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.hudong.b16live.utils.MD5Util;
import com.hudong.core.common.logger.HuDongErrorCodeEnum;
import com.hudong.core.common.logger.HuDongLogger;
import com.hudong.core.common.logger.HuDongLoggerFactory;

/**
 * 进行字符串相加后的md5验证
 * @Title: LegalRequestValidator.java
 * @Description: <br>
 *               <br>
 * @Company: 在线
 * @Created on 2011-9-20 上午10:22:20
 * @author zhaoc
 * @version $Revision: 1.0 $
 * @since 1.0
 */
public class LegalRequestValidator implements ConstraintValidator<LegalRequest, LegalRequestValidatable> {

    private static final HuDongLogger logger = HuDongLoggerFactory.getLogger(LegalRequestValidator.class);

    @Override
    public void initialize(LegalRequest constraintAnnotation) {
    }

    /**
     * 验证输入合法性
     */
    @Override
    public boolean isValid(LegalRequestValidatable value,
                           ConstraintValidatorContext context) {
        //构造原始字符串
        StringBuffer sb = new StringBuffer(value.getSourceString());
        //支持采用自定义密钥
        sb.append("&privateKey=").append((value.getPrivateKey() == null) ? "@baike&hudong*" : value.getPrivateKey());
        if(logger.isDebugEnabled()) {
            logger.debug("[源字符串=" + sb.toString() + "]");
        }

        try {
            //md5
            String serverMd5 = encode2MD5(sb.toString());

            if(logger.isDebugEnabled()) {
                logger.debug("[md5后字符串=" + serverMd5 + "];[客户端签名=" + value.getSign() + "]");
            }

            //验证是否相同
            if(serverMd5.equalsIgnoreCase(value.getSign())) {
                return true;
            }else {
                return false;
            }
        } catch (NoSuchAlgorithmException e) {
            logger.error(HuDongErrorCodeEnum.ProgramError,e.getMessage(),e);
            return false;
        } catch (UnsupportedEncodingException e) {
            logger.error(HuDongErrorCodeEnum.ProgramError,e.getMessage(),e);
            return false;
        }
    }
    public static void main(String[] args) {
        String url = "http://api.hudong.com/mobile/editUserIcon.do?id=1112234352342&mobileType=android&sourceId=1111111&user_iden=GdwFRBHZmXW9da0V8&user_pic_data=123&privateKey=";
    }


    /**
     * md5加密
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String encode2MD5(String inputStr) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        /*MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] bDigests;
        bDigests = md.digest(inputStr.getBytes("UTF-8"));
        String encodeStr = byte2hex(bDigests);
        return encodeStr;*/
        return MD5Util.encode2MD5(inputStr);
    }


}
