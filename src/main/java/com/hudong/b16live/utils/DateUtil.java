
package com.hudong.b16live.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 描        述：时间工具类
 * 创建时间：2016-8-17
 * @author Jibaole
 */
public class DateUtil {
    public static SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat yyyyMMddHH = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String fullDate(){
        return yyyyMMddHHmmss.format(new Date());
    }
    public static String fullDateToMinutes(){
        return yyyyMMddHH.format(new Date());
    }
    /**
     * yyyy-MM-dd HH:mm:ss To Date
     * @return
     */
    public static Date parseFullDate(String date) throws ParseException{
        return yyyyMMddHHmmss.parse(date);
    }


    public static Date parseFullDateToMinutes(String date) throws ParseException{
        return yyyyMMddHH.parse(date);
    }
    /**
     * yyyy-MM-dd To Date
     * @return
     */
    public static Date parseDayDate(String date) throws ParseException{
        return yyyyMMdd.parse(date);
    }
    /**
     * yyyy-MM-dd HH:mm:ss To String
     */
    public static String parseStringFullDate(Date date){
        return yyyyMMddHHmmss.format(date);
    }
    /**
     * yyyy-MM-dd To String
     */
    public static String parseStringDayDate(Date date){
        return yyyyMMdd.format(date);
    }

    /**
     * yyyy_MM
     * @return
     */
    public static String monthDate(){
        return new SimpleDateFormat("yyyy_MM").format(new Date());
    }
    /**
     * yyyy-MM-dd
     * @return
     */
    public static String dayDate(){
        return yyyyMMdd.format(new Date());
    }
    /**
     * 获取当前小时
     * @return
     */
    @SuppressWarnings("deprecation")
    public static int getNowHours(){
        return new Date().getHours();
    }

    /**
     * 计算两个日期之间相差的天数
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate,Date bdate) throws ParseException {
        smdate = yyyyMMdd.parse(yyyyMMdd.format(smdate));
        bdate = yyyyMMdd.parse(yyyyMMdd.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));
    }
    /**
     * 计算两个日期之间相差的天数
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(String smdate,String bdate) throws ParseException {
        Date s= yyyyMMdd.parse(smdate);
        Date b= yyyyMMdd.parse(bdate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(s);
        long time1 = cal.getTimeInMillis();
        cal.setTime(b);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 获取昨天的日期
     * @return
     */
    public static String getYesterday(){
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE, -1);
        return parseStringDayDate(ca.getTime());
    }

    /**
     * 获取本周第一天的日期
     * @return
     */
    public static String getFirstDayOfCurWeek(){
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return parseStringDayDate(ca.getTime());
    }
    /**
     * 获取上周第一天的日期
     * @return
     */
    public static String getFirstDayOfPreWeek(){
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        ca.set(Calendar.DATE, ca.get(Calendar.DATE)-7);
        return parseStringDayDate(ca.getTime());
    }

    /**
     * 获取本周最后一天的日期
     * @return
     */
    public static String getLastDayOfCurWeek(){
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        ca.add(Calendar.WEEK_OF_YEAR, 1);
        return parseStringDayDate(ca.getTime());
    }
    /**
     * 获取上周最后一天的日期
     * @return
     */
    public static String getLastDayOfPreWeek(){
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        ca.add(Calendar.DATE, -1);
        return parseStringDayDate(ca.getTime());
    }


    /**
     * 获取本月第一天的日期
     * @return
     */
    public static String getFirstDayOfCurMonth(){
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DATE, 1);
        return parseStringDayDate(ca.getTime());
    }
    /**
     * 获取上月第一天的日期
     * @return
     */
    public static String getFirstDayOfPreMonth(){
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DATE, 1);
        ca.add(Calendar.MONTH,-1);//减一个月，变为下月的1号
        return parseStringDayDate(ca.getTime());
    }

    /**
     * 获取上月最后一天的日期
     * @return
     */
    public static String getLastMonthforLastDay(){
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DATE,1);//设为当前月的1号
        ca.add(Calendar.DATE,-1);//减去一天，变为当月最后一天
        return parseStringDayDate(ca.getTime());
    }

    /**
     * 获取本月最后一天的日期
     * @return
     */
    public static String getLastDayOfCurMonth(){
        Calendar ca = Calendar.getInstance();
        /**设为当前月的1号*/
        ca.set(Calendar.DATE,1);
        /**加一个月，变为下月的1号*/
        ca.add(Calendar.MONTH,1);
        /**减去一天，变为当月最后一天*/
        ca.add(Calendar.DATE,-1);
        return parseStringDayDate(ca.getTime());
    }

    /**
     * 获取本月最后一天的日期
     * @return
     */
    public static String getLastDayOfPreMonth(){
        Calendar ca = Calendar.getInstance();
        /**设为当前月的1号*/
        ca.set(Calendar.DATE, 1);
        /**减去一天，变为上月最后一天*/
        ca.add(Calendar.DATE,-1);
        return parseStringDayDate(ca.getTime());
    }

    /**
     * 获取某个月的第一天
     * @param month
     * @return
     */
    public static String getFirstDayOfMonth(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return  yyyyMMdd.format(calendar.getTime());
    }
    /**
     * 获取某周的第一天
     * @param week
     * @return
     */
    public static String getFirstDayOfWeek(int week) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_MONTH, week);
        calendar.set(Calendar.DAY_OF_WEEK, 2);
        return  yyyyMMdd.format(calendar.getTime());
    }
    /**
     * yyyyMM
     * @return
     */
    public static String monthDateT(){
        return new SimpleDateFormat("yyyyMM").format(new Date());
    }

    /**
     * 把long 转换成 日期 再转换成String类型
     * @param millSec
     * @return
     */
    public static String transferLongToDate(Long millSec) {
        Date date = new Date(millSec);
        return yyyyMMddHHmmss.format(date);
    }
    /**
     * yyyyMM
     * @return
     */
    public static String monthDateT(String date){
        Date time = null;
        SimpleDateFormat sf =  new SimpleDateFormat("yyyy-MM-dd");
        try {
            time = sf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        return time == null ? format.format(new Date()) : format.format(time);
    }
    public static void main(String[] args) throws ParseException {
		int t = daysBetween("2018-03-05","2018-04-04");
        System.out.println(t);
    }
}
