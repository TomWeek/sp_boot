package com.hudong.b16live.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
/**
 * MD5
 *
 * @Title: MD5.java
 * @Copyright: Copyright (c) 2005
 * @Description:
 * @Company: 百科
 * @Created on 2018/8/8 14:20
 * @Author 90
 */
public class MD5Util {


    public static String encode2MD5(String inputStr) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] bDigests;
        bDigests = md.digest(inputStr.getBytes("UTF-8"));
        //String encodeStr = byte2hex(bDigests);
        return byte2hex(bDigests);
    }
    private static String byte2hex(byte b[]) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 255);
            if (stmp.length() == 1)
                hs = (new StringBuilder(String.valueOf(hs))).append("0")
                        .append(stmp).toString();
            else
                hs = (new StringBuilder(String.valueOf(hs))).append(stmp)
                        .toString();
        }

        return hs.toLowerCase();
    }
    //HmacSHA256加密
    public static String hmacSHA256(String secretKey,String message){
    	String sign = "";
    	try {
    		Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
    	    SecretKeySpec secret_key = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
    	    sha256_HMAC.init(secret_key);
    	    sign = Base64.encodeBase64String(sha256_HMAC.doFinal(message.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return sign;
    }
    //HmacSHA1加密
    public static String hmacSHA1(String secretKey,String message){
    	String sign = "";
    	try {
    		Mac sha1_HMAC = Mac.getInstance("HmacSHA1");
    	    SecretKeySpec secret_key = new SecretKeySpec(secretKey.getBytes(), "HmacSHA1");
    	    sha1_HMAC.init(secret_key);
    	    sign = Base64.encodeBase64String(sha1_HMAC.doFinal(message.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return sign;
    }
    public static void main(String[] args) {
    	String secretKey = "Gu5t9xGARNpq86cd98joQYCN3Cozk1qA";
    	String message = "GETcvm.api.qcloud.com/v2/index.php?Action=DescribeInstances&InstanceIds.0=ins-09dx96dg&Nonce=11886&Region=ap-guangzhou&SecretId=AKIDz8krbsJ5yKBZQpn74WFkmLPx3gnPhESA&SignatureMethod=HmacSHA1&Timestamp=1465185768";
		System.out.println(hmacSHA1(secretKey,message));
	}
}
