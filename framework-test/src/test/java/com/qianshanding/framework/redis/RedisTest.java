package com.qianshanding.framework.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import javax.annotation.Resource;

/**
 * Created by zhengyu on 2017/2/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-redis.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class})
public class RedisTest {
    @Resource
    RedisClient redisClient;

    @Test
    public void testGet() {
        long start = System.currentTimeMillis();
        for (int i = 0; i <= 100000; i++) {
            redisClient.set("fish", "value");
            System.out.println(redisClient.get("fish"));
            redisClient.del("fish");
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
