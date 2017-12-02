package com.cqupt.project.shop.service;

import com.cqupt.project.shop.common.ServerResponse;
import com.cqupt.project.shop.pojo.Product;
import com.cqupt.project.shop.vo.ProductDetailVo;
import com.cqupt.project.shop.vo.ProductListVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author weigs
 * @date 2017/11/29 0029
 */
public interface ProductService {
    /**
     * 更新或插入product
     *
     * @param product
     * @return
     */
    ServerResponse<String> saveOrUpdateProduct(Product product);

    /**
     * 修改商品销售状态
     *
     * @param productId
     * @param status
     * @return
     */
    ServerResponse<String> setSaleStatus(Long productId, Integer status);

    /**
     * 查看商品详情
     *
     * @param productId
     * @return
     */
    ServerResponse<ProductDetailVo> getProductDetail(Long productId);

    /**
     * 分页展示商品
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    ServerResponse<PageInfo> productList(Integer pageNo, Integer pageSize);

    /**
     * 商品搜索功能
     *
     * @param productName
     * @param productId
     * @param pageNo
     * @param pageSize
     * @return
     */
    ServerResponse<PageInfo> searchProduct(String productName, Long productId, Integer pageNo, Integer pageSize);
}
