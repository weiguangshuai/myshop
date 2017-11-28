package com.cqupt.project.shop.controller;

import com.cqupt.project.shop.common.Constant;
import com.cqupt.project.shop.common.ResponseCode;
import com.cqupt.project.shop.common.ServerResponse;
import com.cqupt.project.shop.entity.User;
import com.cqupt.project.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author weigs
 * @date 2017/11/27 0027
 */
@Controller
@RequestMapping(value = "/user/")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session) {
        ServerResponse response = userService.login(username, password);
        if (response.isSuccess()) {
            session.setAttribute(Constant.CURRENT_USER, response.getData());
        }
        return response;
    }

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "register.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> register(User user) {

        return userService.register(user);
    }

    /**
     * 校验
     *
     * @param str
     * @param type
     * @return
     */
    @RequestMapping(value = "check_valid.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> checkValid(String str, String type) {

        return userService.checkValid(str, type);
    }

    /**
     * 获取修改密码问题
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "forget_get_question.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetGetQuestion(String username) {
        return userService.selectQuestion(username);
    }

    /**
     * 验证问题的正确性
     *
     * @param username
     * @param question
     * @param answer
     * @return
     */
    @RequestMapping(value = "check_answer.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> checkAnswer(String username,
                                              String question, String answer) {
        return userService.checkAnswer(username, question, answer);
    }

    /**
     * 重置密码
     *
     * @param username
     * @param newPassword
     * @param token
     * @return
     */
    @RequestMapping(value = "reset_password.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> resetPassword(String username, String newPassword,
                                                String token) {
        return userService.resetPassword(username, newPassword, token);
    }

    /**
     * 修改用户信息
     *
     * @param session
     * @param user
     * @return
     */
    @RequestMapping(value = "update_user_info.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> updateUserInfo(HttpSession session, User user) {
        User currentUser = (User) session.getAttribute(Constant.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        user.setUserId(currentUser.getUserId());
        user.setUsername(currentUser.getUsername());
        ServerResponse<User> response = userService.updateInfo(user);
        if (response.isSuccess()) {
            response.getData().setUsername(currentUser.getUsername());
            session.setAttribute(Constant.CURRENT_USER, response.getData());
        }
        return response;
    }

    /**
     * 获取用户信息
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "get_user_info.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session) {
        User currentUser = (User) session.getAttribute(Constant.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    "未登录，需要登录");
        }
        return userService.getUserInfo(currentUser.getUserId());
    }
}
