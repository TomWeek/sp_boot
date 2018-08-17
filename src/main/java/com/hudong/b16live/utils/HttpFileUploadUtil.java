package com.hudong.b16live.utils;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.*;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;


/**
 * HttpFileUploadUtil
 *
 * @Title: HttpFileUploadUtil.java
 * @Copyright: Copyright (c) 2005
 * @Description:
 * @Company: 百科
 * @Created on 2018/8/9 11:53
 * @Author 90
 */
public class HttpFileUploadUtil {




    public static  void uploadV2(String url, String token,File file){

        PostMethod filePost = new PostMethod(url);
        HttpClient client = new HttpClient();

        try {
            // 通过以下方法可以模拟页面参数提交
            filePost.setParameter("token", "9d3bafa5e44aae15f496e462a64c8963");
            filePost.setParameter("passwd", "xm");

            Part[] parts = { new FilePart(file.getName(), file) };
            filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));

            client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);

            int status = client.executeMethod(filePost);
            if (status == HttpStatus.SC_OK) {
                System.out.println("上传成功");
                String receive = filePost.getResponseBodyAsString();
                System.out.println(receive+"----------php返回值-----------");


            } else {
                System.out.println("上传失败");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            filePost.releaseConnection();
        }
    }

    public static String uploadIconPic(String url, String token, byte[] data , String  des)
            throws UnsupportedEncodingException {
        PostMethod filePost = new PostMethod(url);
        String content = "";
        //token="d9cb5f682c78564b1f4a9a83a734bddd";
        String picUrl = "";
        try {
            Part[] parts = {
                    new FilePart("pic", new ByteArrayPartSource("fileUpload",
                            data)), new StringPart("type", "28"),
                    new StringPart("host", "www.baike.com"),
                    new StringPart("userIden", "QfnEAAAgFAGBzc3V9"),
                    new StringPart("opttype", "0"),
                    new StringPart("token", token),
                    new StringPart("img_src_action", "shitu_upload"),
                    new StringPart("picAlt", des , "utf-8"),
                    new StringPart("img_description", des , "utf-8"),
                    new StringPart("whetherCreateDefaultSize", "false") };
            filePost.setRequestEntity(new MultipartRequestEntity(parts,
                    filePost.getParams()));
            HttpClient client = new HttpClient();
            client.getHttpConnectionManager().getParams()
                    .setConnectionTimeout(30000);
            int status = client.executeMethod(filePost);
            if (status == HttpStatus.SC_OK) {

                content = filePost.getResponseBodyAsString();
                System.out.println(content+"=======================================================php返回ok");
            }
            if (!StringUtils.isBlank(content)) {

                return content;
                /*JSONObject obj = JSONObject.fromObject(content);
                picUrl = (String) obj.get("url");*/
            }
        } catch (Exception ex) {
            return "";
        } finally {
            // filePost.releaseConnection();
        }
        return "";
    }
}
