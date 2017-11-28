package com.cqupt.project.shop.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author weigs
 * @date 2017/11/28 0028
 */
public class TokenCache {
    private static final Logger log = LoggerFactory.getLogger(TokenCache.class);
    public static final String TOKEN_PREFIX = "token_";

    private static LoadingCache<String, String> localCache = CacheBuilder.newBuilder()
            .initialCapacity(1000).maximumSize(10000)
            .expireAfterAccess(5, TimeUnit.MINUTES).build(
                    new CacheLoader<String, String>() {
                        @Override
                        public String load(String s) throws Exception {
                            return "null";
                        }
                    }
            );

    public static String getKey(String key) {
        String value = null;
        try {
            value = localCache.get(key);
            if ("null".equals(value)) {
                return null;
            }
            return value;
        } catch (Exception e) {
            log.error("localCache get error", e);
        }
        return null;
    }

    public static void setKey(String key, String value) {
        localCache.put(key, value);
    }
}
