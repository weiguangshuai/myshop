package com.cqupt.project.shop.controller;

import com.cqupt.project.shop.common.ServerResponse;
import com.cqupt.project.shop.service.ProductService;
import com.cqupt.project.shop.vo.ProductDetailVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author weigs
 * @date 2017/12/2 0002
 */
@Controller
@RequestMapping(value = "/product/")
public class ProductController {
    @Autowired
    private ProductService productService;

    /**
     * 前台展示商品详情
     *
     * @param productId
     * @return
     */
    @RequestMapping(value = "show_product_detail.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<ProductDetailVo> getProductDetail(Long productId) {
        return productService.showProductDetail(productId);
    }

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
    @RequestMapping(value = "product_list.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> list(@RequestParam(value = "keyword", required = false) String keyword,
                                         @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                         @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                         @RequestParam(value = "orderBy", defaultValue = "") String orderBy) {
        return productService.getProductByKeywordCategory(keyword, categoryId,
                pageNo, pageSize, orderBy);
    }
}
