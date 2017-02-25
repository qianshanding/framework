package com.qianshanding.framework.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * TimeZone工具类，如根据时区转化为指定的时间
 *
 * @author Fish Exp
 */
public class TimeZoneHelper {
    /**
     * @param srcFormater   待转化的日期时间的格式(如：yyyy-MM-dd HH:mm:ss)
     * @param srcDateTime   待转化的日期时间
     * @param dstFormater   目标的日期时间的格式(如：yyyy-MM-dd HH:mm:ss)
     * @param dstTimeZoneId 目标的时区编号(如：GMT+08:00)
     * @return 转化后的日期时间
     * @Title: stringToTimezone
     * @Description:将指定日期转化为指定时区的日期
     * @author Fish
     * @date 2016年3月5日
     */
    public static String stringToTimezone(String srcFormater, String srcDateTime, String dstFormater, String dstTimeZoneId) {
        if (srcFormater == null || "".equals(srcFormater))
            return null;
        if (srcDateTime == null || "".equals(srcDateTime))
            return null;
        if (dstFormater == null || "".equals(dstFormater))
            return null;
        if (dstTimeZoneId == null || "".equals(dstTimeZoneId))
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat(srcFormater);
        try {
            int diffTime = getDiffTimeZoneRawOffset(dstTimeZoneId);
            Date date = sdf.parse(srcDateTime);
            long nowTime = date.getTime();
            long newNowTime = nowTime - diffTime;
            date = new Date(newNowTime);
            return dateToString(dstFormater, date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * @return
     * @Title: getDefaultTimeZoneRawOffset
     * @Description:获取系统默认时区和UTC的时间差（单位:毫秒）
     * @author Fish
     * @date 2016年3月5日
     */
    public static int getDefaultTimeZoneRawOffset() {
        return TimeZone.getDefault().getRawOffset();
    }

    /**
     * @param timeZoneId 时区ID
     * @return
     * @Title: getTimeZoneRawOffset
     * @Description:获取指定时区与UTC的时间差.(单位:毫秒)
     * @author Fish
     * @date 2016年3月5日
     */
    public static int getTimeZoneRawOffset(String timeZoneId) {
        return TimeZone.getTimeZone(timeZoneId).getRawOffset();
    }

    /**
     * @param timeZoneId 时区Id
     * @return 获取系统时区与指定时区的时间差.(单位:毫秒)
     * @Title: getDiffTimeZoneRawOffset
     * @Description:获取系统时区与指定时区的时间差.(单位:毫秒)
     * @author Fish
     * @date 2016年3月5日
     */
    private static int getDiffTimeZoneRawOffset(String timeZoneId) {
        return TimeZone.getDefault().getRawOffset() - TimeZone.getTimeZone(timeZoneId).getRawOffset();
    }

    /**
     * @param formater 日期或时间的格式
     * @param date     java.util.Date类的实例
     * @return 日期转化后的字符串
     * @Title: dateToString
     * @Description:日期(时间)转化为字符串
     * @author Fish
     * @date 2016年3月5日
     */
    public static String dateToString(String formater, Date date) {
        return (new SimpleDateFormat(formater)).format(date);
    }
}
