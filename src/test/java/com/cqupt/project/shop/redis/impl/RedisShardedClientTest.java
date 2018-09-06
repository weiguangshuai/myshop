package com.cqupt.project.shop.redis.impl;

import com.cqupt.project.shop.redis.JedisClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author weigs
 * @date 2018/9/6 0006
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:beans.xml")
public class RedisShardedClientTest {

    @Autowired
    private JedisClient jedisClient;

    @Test
    public void get() {
        String result = jedisClient.get("key1");
        System.out.println(result);
    }

    @Test
    public void set() {
        for (int i = 0; i < 10; i++) {
            jedisClient.set("key" + i, "value" + i);
        }
    }

    @Test
    public void hget() {
    }

    @Test
    public void hset() {
    }

    @Test
    public void incr() {
    }

    @Test
    public void expire() {
    }

    @Test
    public void ttl() {
    }

    @Test
    public void del() {
    }

    @Test
    public void hdel() {
    }

    @Test
    public void setEx() {
    }
}