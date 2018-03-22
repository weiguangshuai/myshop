package com.cqupt.project.shop.service.impl;

import com.cqupt.project.shop.common.Constant;
import com.cqupt.project.shop.common.ServerResponse;
import com.cqupt.project.shop.dao.CategoryMapper;
import com.cqupt.project.shop.dao.ProductMapper;
import com.cqupt.project.shop.pojo.Category;
import com.cqupt.project.shop.pojo.Product;
import com.cqupt.project.shop.service.CategoryService;
import com.cqupt.project.shop.service.ProductService;
import com.cqupt.project.shop.util.DateTimeUtil;
import com.cqupt.project.shop.util.PropertiesUtil;
import com.cqupt.project.shop.vo.ProductDetailVo;
import com.cqupt.project.shop.vo.ProductListVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author weigs
 * @date 2017/11/29 0029
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryService categoryService;

    @Override
    public ServerResponse<String> saveOrUpdateProduct(Product product) {
        if (product != null) {
            if (StringUtils.isNotBlank(product.getSubImages())) {
                String[] subImageArray = product.getSubImages().split(",");
                if (subImageArray.length > 0) {
                    product.setMainImage(subImageArray[0]);
                }
            }
            if (product.getProductId() != null) {
                int result = productMapper.updateByPrimaryKey(product);
                if (result > 0) {
                    return ServerResponse.createBySuccessMessage("更新产品成功");
                }
                return ServerResponse.createByErrorMessage("更新产品失败");

            }
            int result = productMapper.insert(product);
            if (result > 0) {
                return ServerResponse.createBySuccessMessage("插入产品成功");
            }
            return ServerResponse.createByErrorMessage("插入产品失败");
        }

        return ServerResponse.createByErrorMessage("参数错误");
    }

    @Override
    public ServerResponse<String> setSaleStatus(Long productId, Integer status) {
        if (productId == null || status == null) {
            return ServerResponse.createByErrorCodeMessage(2, "ILLEGAL_ARGUMENT");
        }
        Product product = new Product();
        product.setProductId(productId);
        product.setStatus(status);

        int result = productMapper.updateByPrimaryKeySelective(product);
        if (result > 0) {
            return ServerResponse.createBySuccessMessage("修改商品销售状态成功");
        }
        return ServerResponse.createByErrorMessage("修改商品销售状态失败");
    }

    @Override
    public ServerResponse<ProductDetailVo> getProductDetail(Long productId) {
        if (productId == null) {
            return ServerResponse.createByErrorMessage("参数错误，不存在");
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            return ServerResponse.createByErrorMessage("产品下架或者删除");
        }
        ProductDetailVo productDetailVo = assembleProductDetailVo(product);
        return ServerResponse.createBySuccess(productDetailVo);
    }

    @Override
    public ServerResponse<PageInfo> productList(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<Product> products = productMapper.selectList();
        List<ProductListVo> productListVos = Lists.newArrayList();
        for (Product productItem : products) {
            ProductListVo productListVo = assembleProductListVo(productItem);
            productListVos.add(productListVo);
        }
        PageInfo<ProductListVo> productPageInfo = new PageInfo<>(productListVos);
        return ServerResponse.createBySuccess(productPageInfo);
    }

    @Override
    public ServerResponse<PageInfo> searchProduct(String productName, Long productId, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        if (StringUtils.isNotBlank(productName)) {
            String productname = "%" + productName + "%";
        }
        List<Product> products = productMapper.selectByNameAndProductId(productName, productId);
        List<ProductListVo> productListVos = Lists.newArrayList();
        for (Product productItem : products) {
            ProductListVo productListVo = assembleProductListVo(productItem);
            productListVos.add(productListVo);
        }
        PageInfo<ProductListVo> pageInfo = new PageInfo<>(productListVos);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<ProductDetailVo> showProductDetail(Long productId) {
        if (productId == null) {
            return ServerResponse.createByErrorMessage("参数错误，不存在");
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            return ServerResponse.createByErrorMessage("产品下架或者删除");
        }
        if (product.getStatus() != Constant.ProductStatusEnum.ON_SALE.getCode()) {
            return ServerResponse.createByErrorMessage("产品已经下架");
        }
        ProductDetailVo productDetailVo = assembleProductDetailVo(product);
        return ServerResponse.createBySuccess(productDetailVo);
    }

    @Override
    public ServerResponse<PageInfo> getProductByKeywordCategory(String keyword,
                                                                Integer categoryId, int pageNo, int pageSize, String orderBy) {

        if (StringUtils.isBlank(keyword) && categoryId == null) {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        List<Integer> categoryIdList = Lists.newArrayList();
        if (categoryId != null) {
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            if (category == null && StringUtils.isBlank(keyword)) {
                //没有分类和关键字，返回一个空的分类
                PageHelper.startPage(pageNo, pageSize);
                List<ProductListVo> productListVos = Lists.newArrayList();
                PageInfo pageInfo = new PageInfo(productListVos);
                return ServerResponse.createBySuccess(pageInfo);
            }
            categoryIdList = categoryService.selectCategoryAndChildrenById(categoryId).getData();
        }
        if (StringUtils.isNotBlank(keyword)) {
//            keyword = "%" + keyword + "%";
            //性能高
            keyword = new StringBuilder().append("%").append(keyword).append("%").toString();
        }
        PageHelper.startPage(pageNo, pageSize);
        if (StringUtils.isNotBlank(orderBy)) {
            if (Constant.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)) {
                String[] orderByArray = orderBy.split("_");
                PageHelper.orderBy(orderByArray[0] + " " + orderByArray[1]);
            }
        }

        List<Product> productList = productMapper.selectByNameAndCategoryIds(StringUtils.isBlank(keyword) ? null : keyword,
                categoryIdList.size() > 0 ? categoryIdList : null);
        List<ProductListVo> productListVoList = Lists.newArrayList();
        for (Product product : productList) {
            productListVoList.add(assembleProductListVo(product));
        }
        PageInfo pageInfo = new PageInfo(productList);
        pageInfo.setList(productListVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    /**
     * 封装产品细节
     *
     * @param product
     * @return
     */
    private ProductDetailVo assembleProductDetailVo(Product product) {
        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setId(product.getProductId());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubImages(product.getSubImages());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setName(product.getName());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setStock(product.getStock());

        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if (category == null) {
            productDetailVo.setParentCategoryId(0);
        } else {
            productDetailVo.setParentCategoryId(category.getParentId());
        }

        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix", "http://localhost:9090"));

        productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
        return productDetailVo;
    }

    /**
     * 封装产品列表
     *
     * @param product
     * @return
     */
    private ProductListVo assembleProductListVo(Product product) {
        ProductListVo productListVo = new ProductListVo();
        productListVo.setId(product.getProductId());
        productListVo.setName(product.getName());
        productListVo.setCategoryId(product.getCategoryId());
        productListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix", "http://img.happymmall.com/"));
        productListVo.setMainImage(product.getMainImage());
        productListVo.setPrice(product.getPrice());
        productListVo.setSubtitle(product.getSubtitle());
        productListVo.setStatus(product.getStatus());

        return productListVo;
    }
}
