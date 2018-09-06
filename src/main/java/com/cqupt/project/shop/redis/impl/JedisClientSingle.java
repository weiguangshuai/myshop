package com.cqupt.project.shop.redis.impl;

import com.cqupt.project.shop.redis.JedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * ━━━━━━oooo━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃stay hungry stay foolish
 * 　　　　┃　　　┃Code is far away from bug with the animal protecting
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 *
 * @author weigs
 * @date 2018/4/14 0014
 */
@Deprecated
public class JedisClientSingle implements JedisClient {
    private static final Logger log = LoggerFactory
            .getLogger(JedisClientSingle.class);

    @Autowired
    private JedisPool jedisPool;


    private Jedis getResource() {
        return jedisPool.getResource();
    }

    @Override
    public String get(String key) {
        return getResource().get(key);
    }

    @Override
    public String set(String key, String value) {
        return getResource().set(key, value);
    }

    @Override
    public String hget(String hkey, String key) {
        return getResource().hget(hkey, key);
    }

    @Override
    public long hset(String hkey, String key, String value) {
        return getResource().hset(hkey, key, value);
    }

    @Override
    public long incr(String key) {
        return getResource().incr(key);
    }

    @Override
    public long expire(String key, Integer second) {
        return getResource().expire(key, second);
    }

    @Override
    public long ttl(String key) {
        return getResource().ttl(key);
    }

    @Override
    public long del(String key) {
        return getResource().del(key);
    }

    @Override
    public long hdel(String hkey, String key) {
        return getResource().hdel(hkey, key);
    }

    @Override
    public String setEx(String key, String value, int exTime) {
        return getResource().setex(key, exTime, value);
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
}
