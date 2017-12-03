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

    @RequestMapping(value = "add.do")
    @ResponseBody
    public ServerResponse<CartVo> add(HttpSession session, Integer count, Long productId) {
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("请登录");
        }
        return cartService.add(user.getUserId(), productId, count);
    }

    @RequestMapping(value = "update.do")
    @ResponseBody
    public ServerResponse<CartVo> update(HttpSession session, Integer count, Long productId) {
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("请登录");
        }
        return cartService.update(user.getUserId(), productId, count);
    }

    @RequestMapping(value = "delete.do")
    @ResponseBody
    public ServerResponse<CartVo> delete(HttpSession session, Integer count, String productIds) {
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("请登录");
        }
        return cartService.deleteProduct(user.getUserId(), productIds);
    }
}
