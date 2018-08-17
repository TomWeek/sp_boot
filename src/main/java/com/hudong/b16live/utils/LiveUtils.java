package com.hudong.b16live.utils;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class LiveUtils {
    
    public final static SimpleDateFormat SDF_HH = new SimpleDateFormat("yyyyMMddHHmmss");

    public static int getRandom() {
        return ThreadLocalRandom.current().nextInt(99999999);
    }

    public static String getTimeStamp() {
        return SDF_HH.format(new Date());
    }
    
    public static String searchClassLoadPath(String file) {
        return getWebRoot() + "/WEB-INF/" + file;
    }

    public static String getWebRoot() {
        URL url = null;
        url = LiveUtils.class.getResource("LiveUtils.class");
        String strURL = url.toString();
        strURL = url.getPath();
        strURL = strURL.substring(strURL.indexOf('/'), strURL.lastIndexOf("/WEB-INF"));
        return strURL;
    }



}
