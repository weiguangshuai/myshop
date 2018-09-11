package com.cqupt.project.shop.controller;

import com.cqupt.project.shop.common.Constant;
import com.cqupt.project.shop.common.ResponseCode;
import com.cqupt.project.shop.common.ServerResponse;
import com.cqupt.project.shop.exception.SystemException;
import com.cqupt.project.shop.pojo.User;
import com.cqupt.project.shop.redis.JedisClient;
import com.cqupt.project.shop.service.UserService;
import com.cqupt.project.shop.util.CookieUtil;
import com.cqupt.project.shop.util.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author weigs
 * @date 2017/11/27 0027
 */
@Controller
@RequestMapping(value = "/user/")
@Api(tags = "用户信息")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JedisClient jedisClient;

    @RequestMapping(value = "home.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse home() throws SystemException {
        if (true) {
            throw new SystemException("weigs");
        }
        return ServerResponse.createBySuccess();
    }

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
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "系统错误"),
            @ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:0,msg:xx,data[{}]},data中的属性参照下方Model",
                    response = User.class)
    })
    @ApiOperation(httpMethod = "POST", value = "系统登录")
    public ServerResponse<User> login(String username, String password, HttpSession session, HttpServletResponse response) {
        ServerResponse result = userService.login(username, password);
        if (result.isSuccess()) {
//            session.setAttribute(Constant.CURRENT_USER, response.getData());
            CookieUtil.writeLoginToken(response, session.getId());
            jedisClient.setEx(session.getId(),
                    JsonUtil.objToString(result.getData()), Constant.RedisCacheExtime.REDIS_SESSION_EXTIME);
        }
        return result;
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
     * @param request
     * @param user
     * @return
     */
    @RequestMapping(value = "update_user_info.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> updateUserInfo(HttpServletRequest request, User user) {
//        User currentUser = (User) session.getAttribute(Constant.CURRENT_USER);
        String token = CookieUtil.readLoginToken(request);
        String userStr = jedisClient.get(token);
        if (StringUtils.isEmpty(userStr)) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        User currentUser = JsonUtil.stringToObj(userStr, User.class);
        user.setUserId(currentUser.getUserId());
        user.setUsername(currentUser.getUsername());
        ServerResponse<User> response = userService.updateInfo(user);
        if (response.isSuccess()) {
            response.getData().setUsername(currentUser.getUsername());
//            session.setAttribute(Constant.CURRENT_USER, response.getData());
            //更新redis中的值
            jedisClient.setEx(token, JsonUtil.objToString(user), Constant.RedisCacheExtime.REDIS_SESSION_EXTIME);
        }
        return response;
    }

    /**
     * 获取用户信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "get_user_info.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpServletRequest request) {
        String token = CookieUtil.readLoginToken(request);
        if (StringUtils.isEmpty(token)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    "未登录，需要登录");
        }
        String userJsonStr = jedisClient.get(token);
        User user = JsonUtil.stringToObj(userJsonStr, User.class);
        if (user != null) {
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMessage("用户未登陆，请登录");
    }
}
