package com.hudong.b16live.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @description：
 * @author：JBL
 * @date：2017/11/27
 */

public class StringTool {

    public static String getMapString(Map paramMap, String mapStr)
    {
        String returnString = (paramMap != null) && (paramMap.get(mapStr) != null) ? paramMap.get(mapStr).toString() : "";

        return returnString;
    }

    public static boolean isEmpty(String str)
    {
        return (str == null) || (str.length() == 0);
    }

    /**
     *
     */
    public static String getDefaultValue( String paraName, String defaultValue) {

        String result = paraName == null ? "" : paraName.trim();
        if (result.equals("")) {
            return defaultValue;
        }
        return result;
    }


    public static int getDefaultValue( String paraName, int defaultValue) {

        String result = getDefaultValue( paraName, String.valueOf(defaultValue));
        int resnum = defaultValue;
        if (!result.equals("")) {
            try {
                resnum = Integer.parseInt(result);
            } catch (Exception e) {

            }
        }
        return resnum;
    }


    public static long getDefaultValue(HttpServletRequest request, String paraName, long defaultValue) {

        String result = getDefaultValue( paraName, String.valueOf(defaultValue));
        long resnum = defaultValue;
        if (!result.equals("")) {
            try {
                resnum = Long.parseLong(result);
            } catch (Exception e) {
            }
        }
        return resnum;
    }

}
