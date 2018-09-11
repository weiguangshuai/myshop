package com.cqupt.project.shop.service;

import com.cqupt.project.shop.common.ServerResponse;
import com.cqupt.project.shop.vo.OrderProductVo;
import com.cqupt.project.shop.vo.OrderVo;
import com.github.pagehelper.PageInfo;

/**
 * @author weigs
 * @date 2017/12/5 0005
 */
public interface OrderService {
    /**
     * 生成订单
     *
     * @param userId
     * @param shipId
     * @return
     */
    ServerResponse createOrder(Long userId, Long shipId);

    /**
     * 取消订单
     *
     * @param userId
     * @param orderNo
     * @return
     */
    ServerResponse<String> cancle(Long userId, Long orderNo);

    /**
     * 获取选中的订单状态
     *
     * @param userId
     * @return
     */
    ServerResponse<OrderProductVo> getOrderCartProduct(Long userId);

    /**
     * 获取订单详情
     *
     * @param userId
     * @param orderNo
     * @return
     */
    ServerResponse<OrderVo> getOrderDetail(Long userId, Long orderNo);

    /**
     * 查看订单列表
     *
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    ServerResponse<PageInfo> listOrder(Long userId, Integer pageNo, Integer pageSize);

    /**
     * 后台查询订单列表
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    ServerResponse<PageInfo> manageList(Integer pageNo, Integer pageSize);

    /**
     * 查看订单详情
     *
     * @param orderNo
     * @return
     */
    ServerResponse<OrderVo> manageDetail(Long orderNo);

    /**
     * 查询
     *
     * @param orderNo
     * @param pageNo
     * @param pageSize
     * @return
     */
    ServerResponse<PageInfo> manageSearch(Long orderNo, Integer pageNo, Integer pageSize);

    /**
     * 发货
     *
     * @param orderNo
     * @return
     */
    ServerResponse<String> manageSendGoods(Long orderNo);


    /**
     * 关闭订单
     *
     * @param hour
     */
    void closeOrder(int hour);
}
