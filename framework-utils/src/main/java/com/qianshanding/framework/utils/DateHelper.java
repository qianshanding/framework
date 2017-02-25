package com.qianshanding.framework.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.qianshanding.framework.utils.support.DateEnum;

/**
 * 时间转化及计算工具类
 *
 * @author Fish
 */
public class DateHelper {
    /**
     * 缺省日期格式
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 缺省时间格式
     */
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    /**
     * 缺省长日期格式,精确到秒
     */
    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 星期数组
     */
    public static final String[] WEEKS = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    /**
     * @return 当前日期的字符串 ,如2012-09-18
     * @Title: today
     * @Description:取当前日期的字符串表示
     * @author Fish
     * @date 2016年3月5日
     */
    public static String today() {
        return today(DEFAULT_DATE_FORMAT);
    }

    /**
     * @param strFormat
     * @return 时间格式
     * @Title: today
     * @Description:根据输入的格式得到当前日期的字符串
     * @author Fish
     * @date 2016年3月5日
     */
    public static String today(String strFormat) {
        return toString(new Date(), strFormat);
    }

    /**
     * @return 当前时间, 如:10:45:00
     * @Title: currentTime
     * @Description: 取当前时间的字符串表示
     * @author Fish
     * @date 2016年3月5日
     */
    public static String currentTime() {
        return currentTime(DEFAULT_TIME_FORMAT);
    }

    /**
     * @param strFormat 输出格式,如'hh:mm:ss'
     * @return 当前时间, 如:10:49:28
     * @Title: currentTime
     * @Description:根据输入的格式获取时间的字符串表示
     * @author Fish
     * @date 2016年3月5日
     */
    public static String currentTime(String strFormat) {
        return toString(new Date(), strFormat);
    }

    /**
     * @param date   源对象
     * @param format 想得到的格式字符串
     * @return 如：2012-09-18
     * @Title: toString
     * @Description:将java.util.date型按照指定格式转为字符串
     * @author Fish
     * @date 2016年3月5日
     */
    public static String toString(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * @param date
     * @return 返回格式：yyyy-MM-dd HH:mm:ss
     * @Title: toString
     * @Description:将java.util.date型按照指定格式转为字符串
     * @author Fish
     * @date 2016年3月28日
     */
    public static String toString(Date date) {
        return new SimpleDateFormat(DEFAULT_DATETIME_FORMAT).format(date);
    }

    /**
     * @param dateType 时间类型{@link DateEnum}
     * @param amount   增加的数量(负数表示减少),如5,-1
     * @return 格式化后的字符串 如"yyyy-MM-dd HH:mm:ss"
     * @throws ParseException
     * @Title: getAddDay
     * @Description: 取得当前时间，之前或之后的天数/月数/年数的日期 <br>
     * 欲取得当前日期5天前的日期,可做如下调用:<br>
     * getAddDay("DATE", -5).
     * @author Fish
     * @date 2016年3月5日
     */
    public static String getAddDay(DateEnum dateType, int amount) throws ParseException {
        return getAddDay(dateType, amount, null);
    }

    /**
     * @param date      指定时间
     * @param dateType  时间类型{@link DateEnum}
     * @param amount    增加的数量(负数表示减少),如5,-1
     * @param strFormat 输出格式,如"yyyy-mm-dd","yyyy-mm-dd hh:mm"
     * @return 格式化后的字符串 如"2010-05-28"
     * @throws ParseException
     * @Title: getAddDay
     * @Description: 根据指定的时间，获取之前或之后的天数/月数/年数的日期 <br>
     * 欲取得当前日期5天前的日期,可做如下调用:<br>
     * getAddDay("DATE", -5, "yyyy-mm-dd hh:mm").
     * @author Fish
     * @date 2016年3月5日
     */
    public static String getAddDay(DateEnum dateType, int amount, String strFormat) throws ParseException {
        return getAddDay(null, dateType, amount, strFormat);
    }

    /**
     * @param date      指定时间
     * @param dateType  时间类型{@link DateEnum}
     * @param amount    增加的数量(负数表示减少),如5,-1
     * @param strFormat 输出格式,如"yyyy-mm-dd","yyyy-mm-dd hh:mm"
     * @return 格式化后的字符串 如"2010-05-28"
     * @throws ParseException
     * @Title: getAddDay
     * @Description: 根据指定的时间，获取之前或之后的天数/月数/年数的日期 <br>
     * 欲取得当前日期5天前的日期,可做如下调用:<br>
     * getAddDay("DATE", -5, "yyyy-mm-dd hh:mm").
     * @author Fish
     * @date 2016年3月5日
     */
    public static String getAddDay(String date, DateEnum dateType, int amount, String strFormat) throws ParseException {
        if (strFormat == null) {
            strFormat = DEFAULT_DATETIME_FORMAT;
        }
        Calendar rightNow = Calendar.getInstance();
        if (date != null && !"".equals(date.trim())) {
            rightNow.setTime(parseDate(date, strFormat));
        }
        rightNow.add(getInterval(dateType), amount);
        return toString(rightNow.getTime(), strFormat);
    }

    /**
     * @param dateType
     * @return 时间类型{@link DateEnum}
     * @Title: getInterval
     * @Description:获取时间间隔类型
     * @author Fish
     * @date 2016年3月5日
     */
    private static int getInterval(DateEnum dateType) {
        switch (dateType) {
            case YEAR:
                return Calendar.YEAR;

            case MONTH:
                return Calendar.MONTH;

            case DAY:
                return Calendar.DATE;

            case HOUR:
                return Calendar.HOUR;

            case MINUTE:
                return Calendar.MINUTE;

            default:
                return Calendar.SECOND;
        }
    }

    /**
     * @param strDate 时间字符串
     * @param format  格式，如："yyyy-MM-dd"
     * @return
     * @throws ParseException
     * @Title: parseDate
     * @Description:根据时间字符串，按照指定的格式，转化为时间类型
     * @author Fish
     * @date 2016年3月5日
     */
    public static Date parseDate(String strDate, String format) throws ParseException {
        return new SimpleDateFormat(format).parse(strDate);
    }

    /**
     * @param strDate 时间字符串
     * @return 时间
     * @throws ParseException
     * @Title: parseDate
     * @Description: 根据时间字符串，使用默认格式"yyyy-MM-dd HH:mm:ss"，转化为时间类型
     * @author Fish
     * @date 2016年3月5日
     */
    public static Date parseDate(String strDate) throws ParseException {
        return new SimpleDateFormat(DEFAULT_DATETIME_FORMAT).parse(strDate);
    }

    /**
     * @return 字符串 如：星期一
     * @throws ParseException
     * @Title: getWeekOfMonth
     * @Description: 获取当前时间的星期数
     * @author Fish
     * @date 2016年3月5日
     */
    public static String getWeekOfMonth() throws ParseException {
        return getWeekOfMonth(null, null);
    }

    /**
     * @param date   定时间
     * @param fromat 时间格式
     * @return 字符串 如：星期一
     * @throws ParseException
     * @Title: getWeekOfMonth
     * @Description:获取指定时间的星期数
     * @author Fish
     * @date 2016年3月5日
     */
    public static String getWeekOfMonth(String date, String fromat) throws ParseException {
        Calendar rightNow = Calendar.getInstance();
        if (date != null && !"".equals(date.trim())) {
            rightNow.setTime(parseDate(date, fromat));
        }
        return WEEKS[rightNow.get(Calendar.WEEK_OF_MONTH)];
    }

    /**
     * @return 取当前日期
     * @Title: getCurDate
     * @Description:取当前日期
     * @author Fish
     * @date 2016年3月5日
     */
    public static Date getCurDate() {
        return (new Date());
    }

    /**
     * @return 时间戳
     * @Title: getCurTimestamp
     * @Description:取当前时间戳
     * @author Fish
     * @date 2016年3月5日
     */
    public static Timestamp getCurTimestamp() {
        return new Timestamp(new Date().getTime());
    }

    /**
     * @param year  年
     * @param month 月
     * @return 该月有几天
     * @Title: getLastDay
     * @Description:获取指定年月，当月有几天
     * @author Fish
     * @date 2016年3月5日
     */
    public static int getLastDay(int year, int month) {
        int day = 1;
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day);
        int last = cal.getActualMaximum(Calendar.DATE);
        return last;
    }

    /**
     * @param fDate 开始时间
     * @param oDate 结束时间
     * @return
     * @Title: daysOfDate
     * @Description:比较两个日期相差的天数
     * @author Fish
     * @date 2016年3月5日
     */
    public static int daysOfDate(Date fDate, Date oDate) {
        long time0 = fDate.getTime();
        long time1 = oDate.getTime();
        return (int) ((time1 - time0) / (1000 * 60 * 60 * 24));
    }
}
