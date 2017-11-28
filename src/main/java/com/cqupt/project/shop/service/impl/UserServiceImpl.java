package com.cqupt.project.shop.service.impl;

import com.cqupt.project.shop.common.Constant;
import com.cqupt.project.shop.common.ServerResponse;
import com.cqupt.project.shop.common.TokenCache;
import com.cqupt.project.shop.dao.UserMapper;
import com.cqupt.project.shop.entity.User;
import com.cqupt.project.shop.service.UserService;
import com.cqupt.project.shop.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
        String MD5password = MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.selectLogin(username, MD5password);
        if (user == null) {
            return ServerResponse.createByErrorMessage("密码错误");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录成功", user);
    }

    @Override
    public ServerResponse<String> checkValid(String str, String type) {
        if (StringUtils.isNotBlank(type)) {
            if (Constant.USERNAME.equals(type)) {
                int resultCount = userMapper.checkUsername(str);
                if (resultCount > 0) {
                    return ServerResponse.createByErrorMessage("用户名已经存在");
                }
            }
            if (Constant.EMAIL.equals(type)) {
                int resultCount = userMapper.checkEmail(str);
                if (resultCount > 0) {
                    return ServerResponse.createByErrorMessage("邮箱已被注册");
                }
            }

        } else {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        return ServerResponse.createBySuccessMessage("校验成功");
    }

    @Override
    public ServerResponse register(User user) {
        ServerResponse<String> validResponse = checkValid(user.getUsername(), Constant.USERNAME);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        validResponse = checkValid(user.getEmail(), Constant.EMAIL);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }

        user.setRole(Constant.Role.ROLE_CUSTOMER);
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int result = userMapper.insert(user);
        if (result == 0) {
            return ServerResponse.createByErrorMessage("注册失败");
        }
        return ServerResponse.createBySuccessMessage("注册成功");
    }

    @Override
    public ServerResponse<String> selectQuestion(String username) {
        ServerResponse<String> response = checkValid(username, Constant.USERNAME);
        if (response.isSuccess()) {
            //用户不存在
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        String question = userMapper.selectQuestionByUsername(username);

        if (StringUtils.isNotBlank(question)) {
            return ServerResponse.createBySuccess(question);
        }
        return ServerResponse.createByErrorMessage("问题为空");
    }

    @Override
    public ServerResponse<String> checkAnswer(String username, String question, String answer) {
        int result = userMapper.checkAnswer(username, question, answer);
        if (result > 0) {
            String token = UUID.randomUUID().toString();
            TokenCache.setKey(TokenCache.TOKEN_PREFIX + username, token);
            return ServerResponse.createBySuccess(token);
        }
        return ServerResponse.createByErrorMessage("问题回答错误");
    }

    @Override
    public ServerResponse<String> resetPassword(String username, String newPassword, String token) {
        if (StringUtils.isBlank(token)) {
            return ServerResponse.createByErrorMessage("参数错误，需要token值");
        }
        ServerResponse<String> response = checkValid(username, Constant.USERNAME);
        if (response.isSuccess()) {
            //用户不存在
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        String tempToken = TokenCache.getKey(TokenCache.TOKEN_PREFIX + username);
        if (StringUtils.isBlank(tempToken)) {
            return ServerResponse.createByErrorMessage("token无效或者过期");
        }
        if (StringUtils.equals(tempToken, token)) {
            int result = userMapper.updateUsername(username,
                    MD5Util.MD5EncodeUtf8(newPassword));
            if (result > 0) {
                return ServerResponse.createBySuccessMessage("修改密码成功");
            }

        } else {
            return ServerResponse.createByErrorMessage("token错误");
        }
        return ServerResponse.createByErrorMessage("修改密码错误");
    }

    @Override
    public ServerResponse<User> updateInfo(User user) {
        //username不能被更新，同时校验email是否存在，如果存在不能用作当前用户的
        int result = userMapper.checkEmailByUserId(user.getEmail(), user.getUserId());
        if (result > 0) {
            return ServerResponse.createByErrorMessage("email已存在，请更换email再试");
        }
        User updateUser = new User();
        updateUser.setUserId(user.getUserId());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setAnswer(user.getAnswer());

        int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if (updateCount > 0) {

            return ServerResponse.createBySuccess("更新个人信息成功", updateUser);
        }

        return ServerResponse.createByErrorMessage("更新个人信息失败");
    }

    @Override
    public ServerResponse<User> getUserInfo(Long userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            return ServerResponse.createByErrorMessage("找不到用户");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);
    }


}
