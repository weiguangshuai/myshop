package com.cqupt.project.shop.service.impl;

import com.cqupt.project.shop.common.ServerResponse;
import com.cqupt.project.shop.dao.UserMapper;
import com.cqupt.project.shop.entity.User;
import com.cqupt.project.shop.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author weigs
 * @date 2017/11/27 0027
 */
@Service
public class UserServiceImpl implements UserService {
    private static Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUsername(username);
        if (resultCount == 0) {
            logger.error("用户名不存在");
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        //todo MD5加密登录

        User user = userMapper.selectLogin(username, password);
        if (user == null) {
            return ServerResponse.createByErrorMessage("密码错误");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录成功", user);
    }


}
