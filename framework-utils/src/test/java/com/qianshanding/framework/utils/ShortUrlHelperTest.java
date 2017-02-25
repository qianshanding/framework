package com.qianshanding.framework.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by zhengyu on 2016/10/19.
 */
public class ShortUrlHelperTest {


    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println("alias:" + ShortUrlHelper.createShortUrl());
        }
        //是否会重复
        Set<String> set = new HashSet<>();
        for (int i = 0; i < 1000000; i++) {
            String shortUrl = ShortUrlHelper.createShortUrl();
            if (!set.contains(shortUrl)) {
                System.out.println(shortUrl);
                set.add(shortUrl);
            } else {
                System.out.println("----");
                break;
            }
        }
    }
}
