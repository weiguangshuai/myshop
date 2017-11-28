package com.cqupt.project.shop.service;

import com.cqupt.project.shop.common.ServerResponse;
import com.cqupt.project.shop.entity.User;

/**
 * @author weigs
 * @date 2017/11/27 0027
 */
public interface UserService {
    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    ServerResponse<User> login(String username, String password);

}
