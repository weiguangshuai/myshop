package com.cqupt.project.shop.dao;

import com.cqupt.project.shop.pojo.Cart;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    Cart selectCartByUserIdProductId(@Param("userId") Long userId, @Param("productId") Long productId);

    List<Cart> getCartByUserId(Long userId);

    int selectCartProductCheckedByUserId(Long userId);

    void deleteByUserIdProductId(@Param("userId") Long userId,
                                 @Param("productList") List<String> productList);
}