package com.cqupt.project.shop.dao;

import com.cqupt.project.shop.pojo.Shipping;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShippingMapper {
    int deleteByPrimaryKey(Long shipId);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Long shipId);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);

    int deleteByShippingIdUserId(@Param("userId") Long userId, @Param("shippingId") Integer shippingId);

    int updateByUserIdShipping(@Param("userId") Long userId, @Param("shipping") Shipping shipping);

    Shipping selectByUserIdShippingId(@Param("userId") Long userId, @Param("shippingId") Integer shippingId);

    List<Shipping> selectByUserId(Long userId);
}