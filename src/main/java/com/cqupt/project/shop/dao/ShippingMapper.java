package com.cqupt.project.shop.dao;

import com.cqupt.project.shop.entity.Shipping;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingMapper {
    int deleteByPrimaryKey(Long shipId);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Long shipId);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);
}