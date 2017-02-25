package com.qianshanding.framework.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 序列化工具类
 *
 * @author Fish Exp
 */
public class SerializationHelper {
    /**
     * @param object
     * @return 对象的字节数组
     * @Title: serializeToByte
     * @Description:序列化对象
     * @author Fish
     * @date 2016年3月5日
     */
    public static byte[] serializeToByte(Object object) {
        if (object == null) {
            return null;
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            new ObjectOutputStream(stream).writeObject(object);
        } catch (IOException e) {
            throw new IllegalArgumentException("无法序列化该对象类型: " + object.getClass(), e);
        }
        return stream.toByteArray();
    }

    /**
     * @param object 序列化对象
     * @return 返回字符串
     * @Title: serializeToString
     * @Description:序列化对象
     * @author Fish
     * @date 2016年3月5日
     */
    public static String serializeToString(Object object) {
        return String.valueOf(serializeToByte(object));
    }

    /**
     * @param bytes 被序列化的字符数组
     * @return 返回反序列化的对象
     * @Title: deserialize
     * @Description:反序列化
     * @author Fish
     * @date 2016年3月5日
     */
    public static Object deserialize(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        try {
            return deserialize(new ObjectInputStream(new ByteArrayInputStream(bytes)));
        } catch (IOException e) {
            throw new IllegalArgumentException("不能反序列化该对象", e);
        }
    }

    /**
     * @param stream 序列化流
     * @return 反序列化对象
     * @Title: deserialize
     * @Description:反序列化对象
     * @author Fish
     * @date 2016年3月5日
     */
    public static Object deserialize(ObjectInputStream stream) {
        if (stream == null) {
            return null;
        }
        try {
            return stream.readObject();
        } catch (IOException e) {
            throw new IllegalArgumentException("不能反序列化该对象", e);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("不能反序列化该对象类型", e);
        }
    }
}
