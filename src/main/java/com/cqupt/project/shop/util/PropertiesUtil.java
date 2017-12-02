package com.cqupt.project.shop.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author weigs
 * @date 2017/11/29 0029
 */
public class PropertiesUtil {
    private static final Logger log = LoggerFactory.getLogger(PropertiesUtil.class);

    private static Properties properties;

    static {
        String filename = "myshop.properties";
        properties = new Properties();
        try {
            properties.load(new InputStreamReader(PropertiesUtil.class
                    .getClassLoader().getResourceAsStream(filename), "UTF-8"));
        } catch (IOException e) {
            log.error("配置文件读取错误", e);
        }

    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return value.trim();
    }

    public static String getProperty(String key, String defaultValue) {
        String value = properties.getProperty(key);
        if (StringUtils.isBlank(value)) {
            value = defaultValue;
        }
        return value.trim();
    }
}
