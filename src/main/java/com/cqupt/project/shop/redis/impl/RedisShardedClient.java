package com.cqupt.project.shop.redis.impl;

import com.cqupt.project.shop.redis.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * @author weigs
 * @date 2018/9/5 0005
 */
@Service
public class RedisShardedClient implements JedisClient {

    @Autowired
    private ShardedJedisPool shardedJedisPool;


    private ShardedJedis getResource() {
        return shardedJedisPool.getResource();
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

    @Override
    public Long setnx(String key, String value) {
        return getResource().setnx(key, value);
    }

    @Override
    public String getset(String key, String value) {
        return getResource().getSet(key, value);
    }
}
