package com.cqupt.project.shop.controller;

import com.cqupt.project.shop.common.Constant;
import com.cqupt.project.shop.common.ServerResponse;
import com.cqupt.project.shop.pojo.User;
import com.cqupt.project.shop.service.CartService;
import com.cqupt.project.shop.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author weigs
 * @date 2017/12/2 0002
 */
@Controller
@RequestMapping(value = "/cart/")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 添加购物车
     *
     * @param session
     * @param count
     * @param productId
     * @return
     */
    @RequestMapping(value = "add.do")
    @ResponseBody
    public ServerResponse<CartVo> add(HttpSession session, Integer count, Long productId) {
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("请登录");
        }
        return cartService.add(user.getUserId(), productId, count);
    }

    /**
     * 更新购物车
     *
     * @param session
     * @param count
     * @param productId
     * @return
     */
    @RequestMapping(value = "update.do")
    @ResponseBody
    public ServerResponse<CartVo> update(HttpSession session, Integer count, Long productId) {
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("请登录");
        }
        return cartService.update(user.getUserId(), productId, count);
    }

    /**
     * 删除购物车
     *
     * @param session
     * @param count
     * @param productIds
     * @return
     */
    @RequestMapping(value = "delete.do")
    @ResponseBody
    public ServerResponse<CartVo> delete(HttpSession session, Integer count, String productIds) {
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("请登录");
        }
        return cartService.deleteProduct(user.getUserId(), productIds);
    }

    /**
     * 列出购物车中所有商品
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "list.do")
    @ResponseBody
    public ServerResponse<CartVo> list(HttpSession session) {
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("请登录");
        }
        return cartService.list(user.getUserId());
    }

    /**
     * 全选或者全不选
     *
     * @param session
     * @param checked
     * @return
     */
    @RequestMapping(value = "select_or_unselect_all.do")
    @ResponseBody
    public ServerResponse<CartVo> selectOrUnselectAll(HttpSession session, Integer checked) {
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("请登录");
        }
        return cartService.selectOrUnSelect(user.getUserId(), null, checked);
    }

    /**
     * 选中或者不选中
     *
     * @param session
     * @param checked
     * @param productId
     * @return
     */
    @RequestMapping(value = "select_or_unselect.do")
    @ResponseBody
    public ServerResponse<CartVo> selectOrUnselect(HttpSession session, Integer checked, Long productId) {
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
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
