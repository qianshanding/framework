package com.qianshanding.framework.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.CRC32;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by zhengyu on 2016/10/19.
 */
public class ShortUrlHelper {


    /**
     * 直接使用此方法，获取alias<br/>
     * 根据当前系统时间(微秒)+数字和字母的范畴，生成alias
     *
     * @return String
     */
    public static String createShortUrl() {
        // 原格式是 1467193993.2738 前面的是毫秒,后面是微秒,精度是4位,有时候3位
        return getShortUrl(getMicroTimeFloat() + random("alnum", 50));
    }

    /**
     * 生成一个时间戳, 格式类似 1467193993.2738, 前面是毫秒, 后面是微秒数, 精度4位
     *
     * @return
     */
    private static String getMicroTimeFloat() {
        // 毫秒数
        long time = Calendar.getInstance().getTimeInMillis();
        return time / 1000 + "." + time % 1000 + random9();
    }

    /**
     * 返回一个随机字符串, 范围是 '' 和 1-9
     *
     * @return
     */
    private static String random9() {
        Random random = new Random();
        int randomInt = random.nextInt(10);
        return 0 == randomInt ? "" : String.valueOf(randomInt);
    }

    public static long getCrc32(String value) {
        CRC32 crc32 = new CRC32();
        crc32.update(value.getBytes());
        return crc32.getValue();
    }


    /**
     * 指定url，生成alias
     *
     * @param url
     * @return String
     */
    public static String getShortUrl(String url) {
        String hash = null;
        if (StringUtils.isBlank(url)) {
            hash = String.valueOf(Calendar.getInstance().getTimeInMillis());
        } else {
            hash = String.valueOf(getCrc32(url));
        }
        Random random = new Random();
        long num = Long.valueOf(hash + String.valueOf(random.nextInt(899) + 100));

        int to = 36;
        String dict = "0123456789abcdefghijklmnopqrstuvwxyz";
        String ret = "";

        char[] dictArray = dict.toCharArray();
        StringBuilder stb = new StringBuilder();
        do {
            char index = dictArray[(int) (num % to)];
            ret = index + ret;
            stb.append(index);
            num = num / to;
        } while (num > 0);
        return ret;
    }

    /**
     * Generates a random string of a given type and length. Possible values for the first argument ($type) are: - alnum
     * - alpha-numeric characters (including capitals) - alpha - alphabetical characters (including capitals) - hexdec -
     * hexadecimal characters, 0-9 plus a-f - numeric - digit characters, 0-9 - nozero - digit characters, 1-9 -
     * distinct - clearly distinct alpha-numeric characters. For values that do not match any of the above, the
     * characters passed in will be used. ##### Example echo Text::random('alpha', 20); // Output: DdyQFCddSKeTkfjCewPa
     * echo Text::random('distinct', 20); // Output: XCDDVXV7FUSYAVXFFKSL
     */
    public static String random(String type, int length) {
        // boolean utf8 = false;
        String pool = null;
        switch (type) {
            case "alnum":
                pool = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
                break;
            case "alpha":
                pool = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
                break;
            case "lowalnum":
                pool = "0123456789abcdefghijklmnopqrstuvwxyz";
                break;
            case "hexdec":
                pool = "0123456789abcdef";
                break;
            case "numeric":
                pool = "0123456789";
                break;
            case "nozero":
                pool = "123456789";
                break;
            case "distinct":
                pool = "2345679ACDEFHJKLMNPRSTUVWXYZ";
                break;
            default:
                pool = type;
                break;
        }

        // 是否utf8字符集的字符串，如果是，则单独处理，否则当普通字符处理
        // utf8 = if(utf8) = pool.

        int max = pool.length();
        char[] poolArray = pool.toCharArray();
        String result = null;
        Random random = new Random();
        char[] stb = new char[length];
        // 从poolArrayl中随机选取一个字符，并拼接
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(max);
            stb[i] = (poolArray[index]);
        }

        String stbStr = new String(stb);
        if (StringUtils.equals(type, "alnum") && length > 1) {
            if (StringUtils.isAlpha(stbStr)) {
                // 如果type是字母，poolArray赋值为(48,57)的随机数
                stb[random.nextInt(length)] = (char) (random.nextInt(9) + 48);
            } else if (StringUtils.isNumeric(stbStr)) {
                // 如果type是数字，poolArray赋值为(65,90)的随机数
                stb[random.nextInt(length)] = (char) (random.nextInt(25) + 65);
            }
        }
        result = StringUtils.join(stb);
        return result;
    }

    /**
     * 判断是否是Ascii码
     *
     * @param str
     * @return boolean
     */
    private boolean isAscii(String str) {
        Pattern pattern = Pattern.compile("/[^\\x00-\\x7F]/S");
        Matcher matcher = pattern.matcher(str);
        boolean flag = matcher.matches();
        return flag;
    }
}
