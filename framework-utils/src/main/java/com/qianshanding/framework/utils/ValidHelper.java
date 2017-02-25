package com.qianshanding.framework.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhengyu on 2016/10/19.
 */
public class ValidHelper {
    private static final Log logger = LogFactory.getLog(ValidHelper.class);

    /**
     * 验证邮箱地址是否正确
     */
    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            logger.error(String.format("[ValidHelper# checkEmail] error input={%s}", email), e);
            flag = false;
        }

        return flag;
    }

    /**
     * 验证手机号码
     *
     * @return [0-9]{5,9}
     */
    public static boolean checkMobile(String tel) {
        boolean flag = false;
        try {
            Pattern p = Pattern.compile("^\\d{11}$");
            Matcher m = p.matcher(tel);
            flag = m.matches();
        } catch (Exception e) {
            logger.error(String.format("[ValidHelper# checkMobile] error input={%s}", tel), e);
            flag = false;
        }
        return flag;
    }

    /**
     * 验证数字（@todo 长度暂时没有限制）
     */
    public static boolean checkNum(String number) {
        boolean flag = false;
        try {
            Pattern p = Pattern.compile("^[0-9]*$");
            Matcher m = p.matcher(number);
            flag = m.matches();
        } catch (Exception e) {
            logger.error(String.format("[ValidHelper# checkNum] error input={%s}", number), e);
            flag = false;
        }
        return flag;
    }

    /**
     * 验证身份证号
     */
    public static boolean checkID(String idNo) {
        boolean flag = false;
        try {
            Pattern p = Pattern.compile("^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$");
            Matcher m = p.matcher(idNo);
            flag = m.matches();
        } catch (Exception e) {
            logger.error(String.format("[ValidHelper# checkID] error input={%s}", idNo), e);
            flag = false;
        }
        return flag;
    }

    /**
     * 校验文本长度是否合法 text/time/image
     */
    public static boolean checkString(String text, int length) {
        int len = text == null ? 0 : text.length();
        return len <= length;
    }

    /**
     * 校验日期格式是否正确
     */
    public static boolean checkDate(String date, String datePattern) {
        boolean flag = true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat(datePattern);
        try {
//      format.setLenient(false);
            format.parse(date);
        } catch (ParseException e) {
            logger.error(String.format("[ValidHelper# checkDate] error input={%s}", date), e);
            flag = false;
        }
        return flag;
    }
}
