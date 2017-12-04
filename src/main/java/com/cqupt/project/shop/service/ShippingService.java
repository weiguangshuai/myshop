package com.cqupt.project.shop.service;

import com.cqupt.project.shop.common.ServerResponse;
import com.cqupt.project.shop.pojo.Shipping;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * @author weigs
 * @date 2017/12/3 0003
 */
public interface ShippingService {
    /**
     * 保存地址
     *
     * @param userId
     * @param shipping
     * @return
     */
    ServerResponse<Map<String, Long>> saveShipping(Long userId, Shipping shipping);

    /**
     * 删除地址，注意横向越权问题
     *
     * @param userId
     * @param shippingId
     * @return
     */
    ServerResponse<String> deleteShipping(Long userId, Integer shippingId);

    /**
     * 更新地址
     *
     * @param userId
     * @param shipping
     * @return
     */
    ServerResponse updateShipping(Long userId, Shipping shipping);

    /**
     * 查看地址详情
     *
     * @param userId
     * @param shippingId
     * @return
     */
    ServerResponse<Shipping> selectShipping(Long userId, Integer shippingId);

    /**
     * 列出所有地址
     *
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    ServerResponse<PageInfo> listShipping(Long userId, Integer pageNo, Integer pageSize);
}
