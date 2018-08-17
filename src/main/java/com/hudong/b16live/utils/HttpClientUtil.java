package com.hudong.b16live.utils;


import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * HttpClient工具类
 */
public class HttpClientUtil {

    /**
     * 日志打印
     */
    private static final Logger logger = Logger.getLogger(HttpClientUtil.class);

    private static HttpClient httpClient =CustomerHttpClient.getHttpClient();

    public static final String CHARSET = "UTF-8";

    static {
        httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
        httpClient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 15000);
    }

    public static String httpGet(String url, Map<String, String> params) {
        return httpGet(url, params, "utf-8");
    }

    public static String httpPost(String url, Map<String, String> params) {
        return httpPost(url, params, "utf-8");
    }

    /**
     * @param url
     * @param params 参数,编码之前的参数
     * @return
     */
    public static String httpGet(String url, Map<String, String> params, String charset) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        try {
            if (params != null && !params.isEmpty()) {
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
                url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
            }
            HttpGet httpget = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpget);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpget.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, "utf-8");
            }
            entity.consumeContent();
            // EntityUtils.consume(entity);
            return result;
        } catch (Exception e) {
            logger.error( "http调用异常！url:" + url, e);
        } finally {
            httpClient.getConnectionManager().closeIdleConnections(30, TimeUnit.SECONDS);
        }
        return null;
    }

    /**
     * @param url
     * @param params 参数,编码之前的参数
     * @return
     */
    public static String httpPost(String url, Map<String, String> params, String charset) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        try {
            HttpPost httpPost = new HttpPost(url);
            if (params != null && !params.isEmpty()) {
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
                httpPost.setEntity(new UrlEncodedFormEntity(pairs, charset));
            }

            HttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, "utf-8");
            }
            entity.consumeContent();
            // EntityUtils.consume(entity);
            return result;
        } catch (Exception e) {
            logger.error( "http调用异常！url:" + url, e);
        } finally {
            httpClient.getConnectionManager().closeIdleConnections(30, TimeUnit.SECONDS);
        }
        return null;
    }

    /**
     * 发送body为form格式
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static String httpPost(String url, Map<String, String> params, HttpEntity requestEntity) throws Exception {
        if (params != null && !params.isEmpty()) {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String value = entry.getValue();
                if (value != null) {
                    pairs.add(new BasicNameValuePair(entry.getKey(), value));
                }
            }
            String queryString = EntityUtils.toString(new UrlEncodedFormEntity(pairs, CHARSET));
            if (url.indexOf("?") > 0) {
                url += "&" + queryString;
            } else {
                url += "?" + queryString;
            }
        }
        HttpPost httpPost = new HttpPost(url);
        if (requestEntity != null) {
            httpPost.setEntity(requestEntity);
        }
        HttpResponse response = httpClient.execute(httpPost);
        try {
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, "utf-8");
            }
            entity.consumeContent();
            // EntityUtils.consume(entity);
            return result;
        } finally {
            response = null;
            // response.close();
        }
    }

    public static String sendPostJson(String url, String xmlBody) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        try {
            HttpPost httpPost = new HttpPost(url);
            //StringEntity reqEntity = new StringEntity(xmlBody);
            StringEntity reqEntity = new StringEntity(xmlBody, "UTF-8");   // 中文乱码在此解决
            // 设置类型
            reqEntity.setContentType("application/json");
            reqEntity.setContentEncoding("utf-8");
            // 设置请求的数据
            httpPost.addHeader("Content-type","application/json; charset=utf-8");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setEntity(reqEntity);
            HttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, "utf-8");
            }
            entity.consumeContent();
            // EntityUtils.consume(entity);
            return result;
        } catch (Exception e) {
            logger.error( "http调用异常！url:" + url, e);
        } finally {
            httpClient.getConnectionManager().closeIdleConnections(30, TimeUnit.SECONDS);
        }
        return null;
    }



}
