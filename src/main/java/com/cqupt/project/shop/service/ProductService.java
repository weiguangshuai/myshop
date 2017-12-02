package com.cqupt.project.shop.service;

import com.cqupt.project.shop.common.ServerResponse;
import com.cqupt.project.shop.pojo.Product;
import com.cqupt.project.shop.vo.ProductDetailVo;
import com.github.pagehelper.PageInfo;

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

    /**
     * 前台展示商品，要判断商品的状态
     *
     * @param productId
     * @return
     */
    ServerResponse<ProductDetailVo> showProductDetail(Long productId);

    /**
     * 动态搜索功能开发
     *
     * @param keyword
     * @param categoryId
     * @param pageNo
     * @param pageSize
     * @param orderBy
     * @return
     */
    ServerResponse<PageInfo> getProductByKeywordCategory(String keyword,
                                                         Integer categoryId, int pageNo, int pageSize, String orderBy);
}
