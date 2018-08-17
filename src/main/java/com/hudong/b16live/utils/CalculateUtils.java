package com.hudong.b16live.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @description：精确精算工具
 * @author：JBL
 * @date：2018/4/3
 */

public class CalculateUtils {
    /**
     * 加法
     *
     * @param var1
     * @param var2
     * @return
     */
    public static double add(double var1, double var2) {
        BigDecimal b1 = new BigDecimal(Double.toString(var1));
        BigDecimal b2 = new BigDecimal(Double.toString(var2));
        return b1.add(b2).doubleValue();

    }

    /**
     * 减法
     *
     * @param var1
     * @param var2
     * @return
     */

    public static double sub(double var1, double var2) {
        BigDecimal b1 = new BigDecimal(Double.toString(var1));
        BigDecimal b2 = new BigDecimal(Double.toString(var2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 乘法
     *
     * @param var1
     * @param var2
     * @return
     */
    public static double mul(double var1, double var2) {
        BigDecimal b1 = new BigDecimal(Double.toString(var1));
        BigDecimal b2 = new BigDecimal(Double.toString(var2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 除法
     *
     * @param v1
     * @param v2
     * @param scale 精度，到小数点后几位
     * @return
     */

    public static double div(double v1, double v2, int scale) {

        if (scale < 0) {

            throw new IllegalArgumentException("The scale must be a positive integer or ");

        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();

    }

    /**
     * 四舍五入
     * @param v
     * @param scale 精确位数
     * @return
     */
    public static double round(double v, int scale) {

        if (scale < 0) {

            throw new IllegalArgumentException("The scale must be a positive integer or zero");

        }

        BigDecimal b = new BigDecimal(Double.toString(v));

        BigDecimal one = new BigDecimal("1");

        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();

    }

    /**
     * 保留2位小数
     * @param v
     * @return
     */
    public static String Format(double v) {
        BigDecimal b = new BigDecimal(Double.toString(v));
        DecimalFormat   df   =new   DecimalFormat("#.00");
        return df.format( v );

    }


    public static void main(String[] args) {
        double a =3.676,b=1.67;
        System.out.println(Format(a));
        System.out.println(Format(CalculateUtils.add( a,b )));
        System.out.println(Format(CalculateUtils.sub( a,b )));
        System.out.println(Format(CalculateUtils.mul( a,b )));
        System.out.println(CalculateUtils.div( a,b ,2));
    }

}
