package com.qsd.framework.test.monitor.config;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by zhengyu on 2016/11/21.
 */
public class ContainerConfig {

    private static Logger logger = LoggerFactory.getLogger(ContainerConfig.class);

    private static Map<String, String> properties = new HashMap<>();

    private static volatile boolean hasInit = false;

    private static Object lock = new Object();

    private static void init() {
        Map<String, String> _properties = new HashMap<>();
        InputStream is = ContainerConfig.class.getClassLoader().getResourceAsStream("container.properties");
        try {
            Properties p = new Properties();
            p.load(is);

            for (Map.Entry<Object, Object> entry : p.entrySet()) {
                _properties.put(entry.getKey().toString(), entry.getValue().toString());
            }

        } catch (IOException e) {
            logger.error("fail to load container.properties.", e);
            throw new RuntimeException("fail to load container.properties.", e);
        }

        properties = Collections.unmodifiableMap(_properties);
    }

    public static String get(String name) {
        if (!hasInit) {
            synchronized (lock) {
                if (!hasInit) {
                    init();
                    hasInit = true;
                }
            }
        }
        return properties.get(name);
    }

    public static String get(String key, String defaultValue) {
        String value = get(key);
        return value == null ? defaultValue : value;
    }

    public static <T> T get(String key, T defaultValue, Class<T> clazz) {
        String value = get(key);
        if (value == null) {
            return defaultValue;
        } else {
            if (clazz.isPrimitive()) {
                return (T) value;
            } else if (isWrapperType(clazz)) {
                try {
                    Method valueOf = clazz.getMethod("valueOf", String.class);
                    T ret = (T) valueOf.invoke(clazz, value);
                    return ret;
                } catch (Exception e) {
                    throw new UnsupportedOperationException(e);
                }
            }
            throw new UnsupportedOperationException(clazz.getName() + " is unsupport class type");
        }

    }

    public static String getMonitorAddr() {
        String configAddr = get("application.monitor.address");
        if (StringUtils.isEmpty(configAddr)) {
            return null;
        } else if (configAddr.startsWith("http:")) {
            return configAddr.replace("http:", "hawk:");
        }
        return configAddr;
    }

    public static String getHawkAddr() {
        String configAddr = get("application.monitor.address");
        if (StringUtils.isEmpty(configAddr)) {
            return null;
        } else if (configAddr.startsWith("hawk:")) {
            return configAddr.replace("hawk:", "http:");
        }
        return configAddr;
    }

    private static boolean isWrapperType(Class clazz) {
        return clazz.equals(String.class) || clazz.equals(Integer.class)
                || clazz.equals(Byte.class) || clazz.equals(Long.class)
                || clazz.equals(Double.class) || clazz.equals(Float.class)
                || clazz.equals(Character.class) || clazz.equals(Short.class)
                || clazz.equals(BigDecimal.class) || clazz.equals(BigInteger.class)
                || clazz.equals(Boolean.class);
    }

}
