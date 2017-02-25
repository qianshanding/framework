package com.qianshanding.framework.utils;

/**
 * IP转化工具类
 *
 * @author Fish Exp 2012-12-3 下午1:26:24
 */
public class IpHelper {
    /**
     * @param ipString ip地址
     * @return long型IP
     * @Title: ipToLong1
     * @Description: ip转化为long类型
     * @author Fish
     * @date 2016年3月5日
     */
    public static long ipToLong1(String ipString) {
        long result = 0;
        java.util.StringTokenizer token = new java.util.StringTokenizer(ipString, ".");
        result += Long.parseLong(token.nextToken()) << 24;
        result += Long.parseLong(token.nextToken()) << 16;
        result += Long.parseLong(token.nextToken()) << 8;
        result += Long.parseLong(token.nextToken());
        return result;
    }

    /**
     * @param longIp
     * @return
     * @Title: longToIP long型IP地址
     * @Description:LONG型IP地址转化为字符串型
     * @author Fish
     * @date 2016年3月5日
     */
    public static String longToIP(long longIp) {
        StringBuffer sb = new StringBuffer("");
        sb.append(String.valueOf((longIp >>> 24)));
        sb.append(".");
        sb.append(String.valueOf((longIp & 0x00FFFFFF) >>> 16));
        sb.append(".");
        sb.append(String.valueOf((longIp & 0x0000FFFF) >>> 8));
        sb.append(".");
        sb.append(String.valueOf((longIp & 0x000000FF)));
        return sb.toString();
    }
}
