package com.cqupt.project.shop.controller;

import com.cqupt.project.shop.common.Constant;
import com.cqupt.project.shop.common.ServerResponse;
import com.cqupt.project.shop.pojo.Shipping;
import com.cqupt.project.shop.pojo.User;
import com.cqupt.project.shop.redis.JedisClient;
import com.cqupt.project.shop.service.ShippingService;
import com.cqupt.project.shop.util.CookieUtil;
import com.cqupt.project.shop.util.JsonUtil;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author weigs
 * @date 2017/12/3 0003
 */
@Controller
@RequestMapping(value = "/shipping/")
public class ShippingController {

    @Autowired
    private ShippingService shippingService;

    @Autowired
    private JedisClient jedisClient;
    /**
     * 添加收货地址
     *
     * @param request
     * @param shipping
     * @return
     */
    @RequestMapping(value = "add_shipping.do")
    @ResponseBody
    public ServerResponse<Map<String, Long>> addShipping(HttpServletRequest request, Shipping shipping) {
        String token = CookieUtil.readLoginToken(request);
        if (StringUtils.isEmpty(token)) {
            return ServerResponse.createByErrorMessage("请登录");
        }
        String userJsonStr = jedisClient.get(token);
        if (StringUtils.isEmpty(userJsonStr)) {
            return ServerResponse.createByErrorMessage("请登录");
        }
        User user = JsonUtil.stringToObj(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.createByErrorMessage("请登录");
        }
        return shippingService.saveShipping(user.getUserId(), shipping);
    }

    /**
     * 删除收货地址
     *
     * @param request
     * @param shippingId
     * @return
     */
    @RequestMapping(value = "delete_shipping.do")
    @ResponseBody
    public ServerResponse deleteShipping(HttpServletRequest request, Integer shippingId) {
        String token = CookieUtil.readLoginToken(request);
        if (StringUtils.isEmpty(token)) {
            return ServerResponse.createByErrorMessage("请登录");
        }
        String userJsonStr = jedisClient.get(token);
        if (StringUtils.isEmpty(userJsonStr)) {
            return ServerResponse.createByErrorMessage("请登录");
        }
        User user = JsonUtil.stringToObj(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.createByErrorMessage("请登录");
        }
        return shippingService.deleteShipping(user.getUserId(), shippingId);
    }

    /**
     * 更新地址
     *
     * @param session
     * @param shipping
     * @return
     */
    @RequestMapping(value = "update_shipping.do")
    @ResponseBody
    public ServerResponse updateShipping(HttpSession session, Shipping shipping) {
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("请登录");
        }
        return shippingService.updateShipping(user.getUserId(), shipping);
    }

    /**
     * 查看地址详情
     *
     * @param request
     * @param shippingId
     * @return
     */
    @RequestMapping(value = "get_shipping.do")
    @ResponseBody
    public ServerResponse<Shipping> getShipping(HttpServletRequest request, Integer shippingId) {
        String token = CookieUtil.readLoginToken(request);
        if (StringUtils.isEmpty(token)) {
            return ServerResponse.createByErrorMessage("请登录");
        }
        String userJsonStr = jedisClient.get(token);
        if (StringUtils.isEmpty(userJsonStr)) {
            return ServerResponse.createByErrorMessage("请登录");
        }
        User user = JsonUtil.stringToObj(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.createByErrorMessage("请登录");
        }
        return shippingService.selectShipping(user.getUserId(), shippingId);
    }

    /**
     * 获取地址列表
     *
     * @param request
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "list_shipping.do")
    @ResponseBody
    public ServerResponse<PageInfo> list(HttpServletRequest request, @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        String token = CookieUtil.readLoginToken(request);
        if (StringUtils.isEmpty(token)) {
            return ServerResponse.createByErrorMessage("请登录");
        }
        String userJsonStr = jedisClient.get(token);
        if (StringUtils.isEmpty(userJsonStr)) {
            return ServerResponse.createByErrorMessage("请登录");
        }
        User user = JsonUtil.stringToObj(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.createByErrorMessage("请登录");
        }
        return shippingService.listShipping(user.getUserId(), pageNo, pageSize);
    }
}
