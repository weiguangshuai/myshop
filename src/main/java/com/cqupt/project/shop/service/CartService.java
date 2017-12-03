package com.cqupt.project.shop.service;

import com.cqupt.project.shop.common.ServerResponse;
import com.cqupt.project.shop.vo.CartVo;

/**
 * @author weigs
 * @date 2017/12/2 0002
 */
public interface CartService {
    /**
     * 添加购物车
     *
     * @param userId
     * @param productId
     * @param count
     * @return
     */
    ServerResponse<CartVo> add(Long userId, Long productId, Integer count);

    /**
     * 更新购物车
     *
     * @param userId
     * @param productId
     * @param count
     * @return
     */
    ServerResponse<CartVo> update(Long userId, Long productId, Integer count);

    /**
     * 删除购物车中商品
     *
     * @param userId
     * @param productIds
     * @return
     */
    ServerResponse<CartVo> deleteProduct(Long userId, String productIds);
}
