package com.qianshanding.framework.utils;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by zhengyu on 2016/10/19.
 */
public class CodeHelperTest {
    @Test
    public void testGenerate() {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < 1000000; i++) {
            String id = CodeHelper.generate();
            if (!set.contains(id)) {
                System.out.println(id);
                set.add(id);
            } else {
                System.out.println("----");
                break;
            }

        }
    }
}
