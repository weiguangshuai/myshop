package com.cqupt.project.shop.controller;

import com.cqupt.project.shop.common.Constant;
import com.cqupt.project.shop.common.ServerResponse;
import com.cqupt.project.shop.pojo.User;
import com.cqupt.project.shop.service.OrderService;
import com.cqupt.project.shop.vo.OrderProductVo;
import com.cqupt.project.shop.vo.OrderVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author weigs
 * @date 2017/12/5 0005
 */
@Controller
@RequestMapping(value = "/order/")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 生成订单
     *
     * @param session
     * @param shipId
     * @return
     */
    @RequestMapping(value = "create.do")
    @ResponseBody
    public ServerResponse createOrder(HttpSession session, Long shipId) {
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(10, "你还没有登录，请登录");
        }
        return orderService.createOrder(user.getUserId(), shipId);
    }

    @RequestMapping(value = "cancle.do")
    @ResponseBody
    public ServerResponse cancleOrder(HttpSession session, Long orderNo) {
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(10, "你还没有登录，请登录");
        }
        return orderService.cancle(user.getUserId(), orderNo);
    }

    /**
     * 获取购物车中勾选商品订单
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "get_order_product.do")
    @ResponseBody
    public ServerResponse<OrderProductVo> getOrderCartProduct(HttpSession session) {
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(10, "你还没有登录，请登录");
        }
        return orderService.getOrderCartProduct(user.getUserId());
    }

    /**
     * 获取订单详情
     *
     * @param session
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "detail.do")
    @ResponseBody
    public ServerResponse<OrderVo> detail(HttpSession session, Long orderNo) {
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(10, "你还没有登录，请登录");
        }
        return orderService.getOrderDetail(user.getUserId(), orderNo);
    }

    /**
     * 查询订单列表
     *
     * @param session
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "list.do")
    @ResponseBody
    public ServerResponse<PageInfo> OrderList(HttpSession session,
                                              @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                              @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(10, "你还没有登录，请登录");
        }
        return orderService.listOrder(user.getUserId(), pageNo, pageSize);
    }

}
