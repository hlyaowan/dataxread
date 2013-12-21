/*
 * 
 */
// Created on 2013-4-3

package com.taobao.datax.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author joe.chen
 */
public abstract class DateUtils {

    public static Timestamp getDateTime() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        timestamp.setNanos(0);
        return timestamp;
    }

    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmsssss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static String getStringDate(java.sql.Timestamp time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmsssss");
        String dateString = formatter.format(time);
        return dateString;
    }

    public static Timestamp parseDateTime(String dataTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return new Timestamp(sdf.parse(dataTime).getTime());
        } catch (ParseException e) {

        }
        return null;
    }
    
    public static Timestamp parseDateNowTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date =new  Date();
        try {
            return new Timestamp(sdf.parse(sdf.format(date)).getTime());
        } catch (ParseException e) {

        }
        return null;
    }

    public static Date parseDate(String dataTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(dataTime);
        } catch (ParseException e) {
        }
        return null;
    }

    /**
     * 取得下一天开始unix time
     * 
     * @return
     */
    public static long getNextDayUnixTime() {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.add(Calendar.DATE, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis() / 1000;
    }
    
    /**
     * 取得下一天开始unix time
     * 
     * @return
     */
    public static long getCurrentUnixTime() {
        java.util.Calendar c = java.util.Calendar.getInstance();
        return c.getTimeInMillis() / 1000;
    }

    /**
     * 取得下一月开始unix time
     * 
     * @return
     */
    public static long getNextMonthUnixTime() {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis() / 1000;
    }

    public static void main(String[] args) {
        System.out.println(getCurrentUnixTime()-3600 * 24 * 30);
    }

}
