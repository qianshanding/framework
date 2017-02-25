package com.qianshanding.framework.utils;

import java.math.BigDecimal;

/**
 * 算术运算，加减乘除等等
 *
 * @author Fish
 */
public class ArithmeticHelper {

    /**
     * 默认除法运算精度
     **/
    private static final int DEF_DIV_SCALE = 10;

    private ArithmeticHelper() {

    }

    /**
     * @param summand 被加数
     * @param addend  加数
     * @return
     * @Title: add
     * @Description:提供精确的加法运算。
     * @author Fish
     * @date 2016年3月5日
     */
    public static double add(double summand, double addend) {
        BigDecimal bd1 = new BigDecimal(Double.toString(addend));
        BigDecimal bd2 = new BigDecimal(Double.toString(summand));
        return bd1.add(bd2).doubleValue();

    }

    /**
     * @param minuend    被减数
     * @param subtrahend 减数
     * @return
     * @Title: sub
     * @Description:提供精确的减法运算。
     * @author Fish
     * @date 2016年3月5日
     */
    public static double sub(double minuend, double subtrahend) {
        BigDecimal b1 = new BigDecimal(Double.toString(minuend));
        BigDecimal b2 = new BigDecimal(Double.toString(subtrahend));
        return b1.subtract(b2).doubleValue();

    }

    /**
     * @param multiplicand 被乘数
     * @param multiplier   乘数
     * @return 两个参数的积
     * @Title: mul
     * @Description:提供精确的乘法运算。
     * @author Fish
     * @date 2016年3月5日
     */
    public static double mul(double multiplicand, double multiplier) {
        BigDecimal b1 = new BigDecimal(Double.toString(multiplicand));
        BigDecimal b2 = new BigDecimal(Double.toString(multiplier));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * @param dividend 被除数
     * @param divisor  除数
     * @return
     * @Title: div
     * @Description:提供（相对）精确的除法运算，当发生除不尽的情况时，精确到小数点以后10位，以后的数字四舍五入。
     * @author Fish
     * @date 2016年3月5日
     */
    public static double div(double dividend, double divisor) {
        return div(dividend, divisor, DEF_DIV_SCALE);
    }

    /**
     * @param dividend 被除数
     * @param divisor  除数
     * @param scale    表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     * @Title: div
     * @Description:提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入。
     * @author Fish
     * @date 2016年3月5日
     */
    public static double div(double dividend, double divisor, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(dividend));
        BigDecimal b2 = new BigDecimal(Double.toString(divisor));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * @param value 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return
     * @Title: round
     * @Description:提供精确的小数位四舍五入处理。
     * @author Fish
     * @date 2016年3月5日
     */
    public static double round(double value, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal bd = new BigDecimal(Double.toString(value));
        return bd.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();

    }
}
