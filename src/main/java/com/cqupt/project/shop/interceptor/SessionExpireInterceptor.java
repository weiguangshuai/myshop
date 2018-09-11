package com.cqupt.project.shop.interceptor;

import com.cqupt.project.shop.common.Constant;
import com.cqupt.project.shop.pojo.User;
import com.cqupt.project.shop.redis.JedisClient;
import com.cqupt.project.shop.util.CookieUtil;
import com.cqupt.project.shop.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author weigs
 * @date 2018/9/8 0008
 */
public class SessionExpireInterceptor implements HandlerInterceptor {

    @Autowired
    private JedisClient jedisClient;

    //每次请求接口都对session的时间进行一个重置
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if (StringUtils.isNotEmpty(loginToken)) {
            String userJsonStr = jedisClient.get(loginToken);
            User user = JsonUtil.stringToObj(userJsonStr, User.class);
            if (user != null) {
                //不为空，重置时间
                jedisClient.expire(loginToken, Constant.RedisCacheExtime.REDIS_SESSION_EXTIME);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
