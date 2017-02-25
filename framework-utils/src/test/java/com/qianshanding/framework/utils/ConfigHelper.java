package com.qianshanding.framework.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by zhengyu on 2016/10/19.
 */
public class ConfigHelper {
    private static final Log logger = LogFactory.getLog(ValidHelper.class);

    private static Map<String, String> properties = new HashMap<>();

    private static volatile boolean hasInit = false;

    private static Object lock = new Object();

    private static void init() {
        Map<String, String> _properties = new HashMap<>();
        InputStream is = ConfigHelper.class.getClassLoader().getResourceAsStream("config.properties");
        try {
            Properties p = new Properties();
            p.load(is);

            for (Map.Entry<Object, Object> entry : p.entrySet()) {
                _properties.put(entry.getKey().toString(), entry.getValue().toString());
            }

        } catch (IOException e) {
            logger.error("fail to load config.properties.", e);
            throw new RuntimeException("fail to load config.properties.", e);
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

    public static void main(String args[]) {
        System.out.println(ConfigHelper.get("fish"));
    }
}
