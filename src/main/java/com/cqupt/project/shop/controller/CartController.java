package com.cqupt.project.shop.controller;

import com.cqupt.project.shop.common.ServerResponse;
import com.cqupt.project.shop.pojo.User;
import com.cqupt.project.shop.redis.JedisClient;
import com.cqupt.project.shop.service.CartService;
import com.cqupt.project.shop.util.CookieUtil;
import com.cqupt.project.shop.util.JsonUtil;
import com.cqupt.project.shop.vo.CartVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author weigs
 * @date 2017/12/2 0002
 */
@Controller
@RequestMapping(value = "/cart/")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private JedisClient jedisClient;

    @RequestMapping(value = "home.do")
    @ResponseBody
    public String list() {
        return "list";
    }
    /**
     * 添加购物车
     *
     * @param request
     * @param count
     * @param productId
     * @return
     */
    @RequestMapping(value = "add.do")
    @ResponseBody
    public ServerResponse<CartVo> add(HttpServletRequest request, Integer count, Long productId) {
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
        return cartService.add(user.getUserId(), productId, count);
    }

    /**
     * 更新购物车
     *
     * @param request
     * @param count
     * @param productId
     * @return
     */
    @RequestMapping(value = "update.do")
    @ResponseBody
    public ServerResponse<CartVo> update(HttpServletRequest request, Integer count, Long productId) {
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
        return cartService.update(user.getUserId(), productId, count);
    }

    /**
     * 删除购物车
     *
     * @param request
     * @param count
     * @param productIds
     * @return
     */
    @RequestMapping(value = "delete.do")
    @ResponseBody
    public ServerResponse<CartVo> delete(HttpServletRequest request, Integer count, String productIds) {
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
        return cartService.deleteProduct(user.getUserId(), productIds);
    }

    /**
     * 列出购物车中所有商品
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "list.do")
    @ResponseBody
    public ServerResponse<CartVo> list(HttpServletRequest request) {
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
        return cartService.list(user.getUserId());
    }

    /**
     * 全选或者全不选
     *
     * @param request
     * @param checked
     * @return
     */
    @RequestMapping(value = "select_or_unselect_all.do")
    @ResponseBody
    public ServerResponse<CartVo> selectOrUnselectAll(HttpServletRequest request, Integer checked) {
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
        return cartService.selectOrUnSelect(user.getUserId(), null, checked);
    }

    /**
     * 选中或者不选中
     *
     * @param request
     * @param checked
     * @param productId
     * @return
     */
    @RequestMapping(value = "select_or_unselect.do")
    @ResponseBody
    public ServerResponse<CartVo> selectOrUnselect(HttpServletRequest request, Integer checked, Long productId) {
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
        return cartService.selectOrUnSelect(user.getUserId(), productId, checked);
    }

    /**
     * 获取购物车商品数量
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "get_cart_product_num.do")
    @ResponseBody
    public ServerResponse<Integer> getCartProductNum(Long userId) {
        return cartService.getCartProductCount(userId);
    }
}
