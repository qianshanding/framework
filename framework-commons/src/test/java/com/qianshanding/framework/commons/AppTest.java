package com.qianshanding.framework.commons;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Unit test for simple App.
 */
public class AppTest {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println(sdf.parse("2016-08-02 00:00:00").getTime());
    }
}
