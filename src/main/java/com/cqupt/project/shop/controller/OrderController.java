package com.cqupt.project.shop.controller;

import com.cqupt.project.shop.common.ServerResponse;
import com.cqupt.project.shop.pojo.User;
import com.cqupt.project.shop.redis.JedisClient;
import com.cqupt.project.shop.service.OrderService;
import com.cqupt.project.shop.util.CookieUtil;
import com.cqupt.project.shop.util.JsonUtil;
import com.cqupt.project.shop.vo.OrderProductVo;
import com.cqupt.project.shop.vo.OrderVo;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author weigs
 * @date 2017/12/5 0005
 */
@Controller
@RequestMapping(value = "/order/")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private JedisClient jedisClient;


    /**
     * 生成订单
     *
     * @param request
     * @param shipId
     * @return
     */
    @RequestMapping(value = "create.do")
    @ResponseBody
    public ServerResponse createOrder(HttpServletRequest request, Long shipId) {
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
            return ServerResponse.createByErrorCodeMessage(10, "你还没有登录，请登录");
        }
        return orderService.createOrder(user.getUserId(), shipId);
    }

    @RequestMapping(value = "cancle.do")
    @ResponseBody
    public ServerResponse cancleOrder(HttpServletRequest request, Long orderNo) {
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
            return ServerResponse.createByErrorCodeMessage(10, "你还没有登录，请登录");
        }
        return orderService.cancle(user.getUserId(), orderNo);
    }

    /**
     * 获取购物车中勾选商品订单
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "get_order_product.do")
    @ResponseBody
    public ServerResponse<OrderProductVo> getOrderCartProduct(HttpServletRequest request) {
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
            return ServerResponse.createByErrorCodeMessage(10, "你还没有登录，请登录");
        }
        return orderService.getOrderCartProduct(user.getUserId());
    }

    /**
     * 获取订单详情
     *
     * @param request
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "detail.do")
    @ResponseBody
    public ServerResponse<OrderVo> detail(HttpServletRequest request, Long orderNo) {
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
            return ServerResponse.createByErrorCodeMessage(10, "你还没有登录，请登录");
        }
        return orderService.getOrderDetail(user.getUserId(), orderNo);
    }

    /**
     * 查询订单列表
     *
     * @param request
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "list.do")
    @ResponseBody
    public ServerResponse<PageInfo> OrderList(HttpServletRequest request,
                                              @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
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
            return ServerResponse.createByErrorCodeMessage(10, "你还没有登录，请登录");
        }
        return orderService.listOrder(user.getUserId(), pageNo, pageSize);
    }

}
