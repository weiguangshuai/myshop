package com.cqupt.project.shop.service.impl;

import com.cqupt.project.shop.common.ServerResponse;
import com.cqupt.project.shop.dao.ShippingMapper;
import com.cqupt.project.shop.pojo.Shipping;
import com.cqupt.project.shop.service.ShippingService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author weigs
 * @date 2017/12/3 0003
 */
@Service
public class ShippingServiceImpl implements ShippingService {
    @Autowired
    private ShippingMapper shippingMapper;

    @Override
    public ServerResponse<Map<String, Long>> saveShipping(Long userId, Shipping shipping) {
        shipping.setUserId(userId);
        int rowCount = shippingMapper.insert(shipping);
        if (rowCount > 0) {
            Map<String, Long> resultMap = Maps.newHashMap();
            resultMap.put("shippingId", shipping.getShipId());
            return ServerResponse.createBySuccess("新建地址成功", resultMap);
        }
        return ServerResponse.createByErrorMessage("新建地址失败");
    }

    @Override
    public ServerResponse<String> deleteShipping(Long userId, Integer shippingId) {
        int resultCount = shippingMapper.deleteByShippingIdUserId(userId, shippingId);
        if (resultCount > 0) {
            return ServerResponse.createBySuccessMessage("删除成功");
        }
        return ServerResponse.createByErrorMessage("删除失败");
    }

    @Override
    public ServerResponse updateShipping(Long userId, Shipping shipping) {
        shipping.setUserId(userId);
        int result = shippingMapper.updateByUserIdShipping(userId, shipping);
        if (result > 0) {
            return ServerResponse.createBySuccessMessage("更新成功");
        }
        return ServerResponse.createByErrorMessage("更新失败");
    }

    @Override
    public ServerResponse<Shipping> selectShipping(Long userId, Integer shippingId) {

        Shipping shipping = shippingMapper.selectByUserIdShippingId(userId, shippingId);
        if (shipping == null) {
            return ServerResponse.createByErrorMessage("未找到地址");
        }
        return ServerResponse.createBySuccess(shipping);
    }

    @Override
    public ServerResponse<PageInfo> listShipping(Long userId, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<Shipping> shippingList = shippingMapper.selectByUserId(userId);
        PageInfo pageInfo = new PageInfo(shippingList);
        return ServerResponse.createBySuccess(pageInfo);
    }
}
