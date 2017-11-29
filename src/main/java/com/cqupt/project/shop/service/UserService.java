package com.cqupt.project.shop.service;

import com.cqupt.project.shop.common.ServerResponse;
import com.cqupt.project.shop.pojo.User;

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

    /**
     * 验证用户名和邮箱
     *
     * @param str
     * @param type
     * @return
     */
    ServerResponse<String> checkValid(String str, String type);

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    ServerResponse<String> register(User user);

    /**
     * 查找修改密码的问题
     *
     * @param username
     * @return
     */
    ServerResponse<String> selectQuestion(String username);

    /**
     * 检查答案
     *
     * @param username
     * @param question
     * @param answer
     * @return
     */
    ServerResponse<String> checkAnswer(String username, String question, String answer);

    /**
     * 重置密码
     *
     * @param username
     * @param newPassword
     * @param token
     * @return
     */
    ServerResponse<String> resetPassword(String username,
                                         String newPassword, String token);

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    ServerResponse<User> updateInfo(User user);

    /**
     * 获取用户信息
     *
     * @param userId
     * @return
     */
    ServerResponse<User> getUserInfo(Long userId);

    /**
     * 校验是否为管理员
     *
     * @param user
     * @return
     */
    ServerResponse checkAdminRole(User user);
}
