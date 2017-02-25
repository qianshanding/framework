package com.qianshanding.framework.utils;

/**
 * Created by zhengyu on 2017/1/23.
 */
public class UaHelper {

    public static boolean isApp(String ua) {
        if (ua.contains("iPhone")) {
            return true;
        }
        if (ua.contains("Android")) {
            return true;
        }
        if (ua.contains("iPod")) {
            return true;
        }
        if (ua.contains("iPad")) {
            return true;
        }
        if (ua.contains("Mobile")) {
            return true;
        }
        if (ua.contains("Symbian")) {
            return true;
        }
        return false;
    }

    public static boolean isPC(String ua) {
        if (ua.contains("Safari")) {
            return true;
        }
        if (ua.contains("Mozilla")) {
            return true;
        }
        if (ua.contains("Firefox")) {
            return true;
        }
        if (ua.contains("Opera")) {
            return true;
        }
        if (ua.contains("Chrome")) {
            return true;
        }
        return false;
    }

    public static boolean isAppSDK(String ua) {
        if (ua.contains("kdtUnion_")) {
            return true;
        }
        return false;
    }

    public static boolean isOpenSDK(String ua) {
        if (ua.contains("X-KDT-Client")) {
            return true;
        }
        if (ua.contains("KdtApiSdk")) {
            return true;
        }
        return false;
    }

    public static boolean isProgram(String ua) {
        if (ua.contains("okhttp")) {
            return true;
        }
        if (ua.contains("HttpClient")) {
            return true;
        }
        if (ua.contains("HttpAsyncClient")) {
            return true;
        }
        if (ua.contains("Java")) {
            return true;
        }
        if (ua.contains("ISCS-NET")) {
            return true;
        }
        if (ua.contains("Python-urllib")) {
            return true;
        }
        if (ua.contains("libcurl")) {
            return true;
        }
        if (ua.contains("Go-http-client")) {
            return true;
        }
        return false;
    }
}