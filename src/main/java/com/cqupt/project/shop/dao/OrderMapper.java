package com.cqupt.project.shop.dao;

import com.cqupt.project.shop.pojo.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    Order selectByUserIdOrderNo(@Param("userId") Long userId, @Param("orderNo") Long orderNo);

    List<Order> selectByUserId(Long userId);

    List<Order> selectAllOrder();

    Order selectByOrderNo(Long orderNo);

    List<Order> selectOrderStatusByCreateTime(@Param("status") Integer status,
                                              @Param("date") String date);

    int closeOrderByOrderId(Long orderId);
}