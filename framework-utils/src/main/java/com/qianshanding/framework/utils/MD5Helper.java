package com.qianshanding.framework.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密解密
 *
 * @author Fish Exp
 */
public class MD5Helper {
    private final static String KEY_MD5 = "MD5";
    private final static String KEY_SHA = "SHA-512";

    private static char hexChars[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * @param value 加密串
     * @return
     * @Title: encrypt32MD5
     * @Description:返回32位MD5加密密码
     * @author Fish
     * @date 2016年3月5日
     */
    public static String encrypt32MD5(String value) {
        try {
            byte[] bytes = value.getBytes();
            MessageDigest md = MessageDigest.getInstance(KEY_MD5);

            md.update(bytes);
            bytes = md.digest();
            int j = bytes.length;
            char[] chars = new char[j * 2];
            int k = 0;
            for (int i = 0; i < bytes.length; i++) {
                byte b = bytes[i];
                chars[k++] = hexChars[b >>> 4 & 0xf];
                chars[k++] = hexChars[b & 0xf];
            }
            return new String(chars);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * @param value 加密串
     * @return
     * @Title: encrypt16MD5
     * @Description:返回16位MD5加密密码
     * @author Fish
     * @date 2016年3月5日
     */
    public static String encrypt16MD5(String value) {
        try {
            byte[] bytes = value.getBytes();
            MessageDigest md = MessageDigest.getInstance(KEY_MD5);
            md.update(bytes);
            bytes = md.digest();
            int j = bytes.length;
            char[] chars = new char[j * 2];
            int k = 0;
            for (int i = 0; i < bytes.length; i++) {
                byte b = bytes[i];
                chars[k++] = hexChars[b >>> 4 & 0xf];
                chars[k++] = hexChars[b & 0xf];
            }
            return new String(chars).substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * @param value 加密串
     * @return
     * @Title: encrypt32SHA
     * @Description:返回32位SHA加密密码(注：SHA比md5安全)
     * @author Fish
     * @date 2016年3月5日
     */
    public static String encrypt32SHA(String value) {
        try {
            byte[] bytes = value.getBytes();
            MessageDigest md = MessageDigest.getInstance(KEY_SHA);

            md.update(bytes);
            bytes = md.digest();
            int j = bytes.length;
            char[] chars = new char[j * 2];
            int k = 0;
            for (int i = 0; i < bytes.length; i++) {
                byte b = bytes[i];
                chars[k++] = hexChars[b >>> 4 & 0xf];
                chars[k++] = hexChars[b & 0xf];
            }
            return new String(chars);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * @param value 加密串
     * @return
     * @Title: encrypt16SHA
     * @Description:返回16位SHA加密密码(SHA比md5安全)
     * @author Fish
     * @date 2016年3月5日
     */
    public static String encrypt16SHA(String value) {
        try {
            byte[] bytes = value.getBytes();
            MessageDigest md = MessageDigest.getInstance(KEY_SHA);
            md.update(bytes);
            bytes = md.digest();
            int j = bytes.length;
            char[] chars = new char[j * 2];
            int k = 0;
            for (int i = 0; i < bytes.length; i++) {
                byte b = bytes[i];
                chars[k++] = hexChars[b >>> 4 & 0xf];
                chars[k++] = hexChars[b & 0xf];
            }
            return new String(chars).substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * @param file 文件
     * @return
     * @throws IOException
     * @Title: fileMD5
     * @Description:获取文件的MD5值
     * @author Fish
     * @date 2016年3月5日
     */
    public static String fileMD5(File file) throws IOException {
        MessageDigest messagedigest;
        FileInputStream in = null;
        try {
            messagedigest = MessageDigest.getInstance(KEY_MD5);
            in = new FileInputStream(file);
            FileChannel ch = in.getChannel();
            MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            messagedigest.update(byteBuffer);
            return bufferToHex(messagedigest.digest());
        } catch (NoSuchAlgorithmException e) {
            return null;
        } finally {
            in.close();
        }
    }

    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexChars[(bt & 0xf0) >> 4];
        char c1 = hexChars[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }
}
