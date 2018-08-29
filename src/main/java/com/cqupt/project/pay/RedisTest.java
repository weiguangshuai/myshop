package com.cqupt.project.pay;

import redis.clients.jedis.Jedis;

/**
 * @author weigs
 * @date 2018/5/22 0022
 */
public class RedisTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.set("ddd","weigs");
        System.out.println(jedis.get("ddd"));
    }
}
