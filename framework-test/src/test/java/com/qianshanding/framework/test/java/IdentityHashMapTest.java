package com.qianshanding.framework.test.java;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * IdentityHashMap中，是判断key是否为同一个对象(同一个内存地址，同一个内存地址的对象会覆盖value)，
 * 而不是普通HashMap的equals方式判断
 * Created by zhengyu on 2016/11/24.
 */
public class IdentityHashMapTest {
    public static void main(String[] args) {
        IdentityHashMap<String, Object> map = new IdentityHashMap<String, Object>();
        String xx = new String("xx");
        map.put(new String("xx"), "first");
        //同一个内存地址会覆盖
        map.put(xx, "second1");
        map.put(xx, "second2");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.print(entry.getKey() + "    ");
            System.out.println(entry.getValue());
        }
        System.out.println("idenMap=" + map.containsKey("xx"));
        System.out.println("idenMap=" + map.get(xx));
    }
}
