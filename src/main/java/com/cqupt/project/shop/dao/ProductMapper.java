package com.cqupt.project.shop.dao;

import com.cqupt.project.shop.pojo.Product;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMapper {
    int deleteByPrimaryKey(Long productId);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Long productId);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> selectList();

    List<Product> selectByNameAndProductId(@Param("productName") String productName, @Param("productId") Long productId);

    List<Product> selectByNameAndCategoryIds(@Param("keyword") String keyword, @Param("categoryIdList") List<Integer> categoryIdList);
}