package com.cqupt.project.shop.service.impl;

import com.cqupt.project.shop.common.Constant;
import com.cqupt.project.shop.common.ServerResponse;
import com.cqupt.project.shop.dao.CartMapper;
import com.cqupt.project.shop.dao.ProductMapper;
import com.cqupt.project.shop.pojo.Cart;
import com.cqupt.project.shop.pojo.Product;
import com.cqupt.project.shop.redis.JedisClient;
import com.cqupt.project.shop.service.CartService;
import com.cqupt.project.shop.util.BigDecimalUtil;
import com.cqupt.project.shop.util.GsonConvertUtil;
import com.cqupt.project.shop.util.PropertiesUtil;
import com.cqupt.project.shop.vo.CartProductVo;
import com.cqupt.project.shop.vo.CartVo;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author weigs
 * @date 2017/12/2 0002
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private JedisClient jedisClient;

    @Override
    public ServerResponse<CartVo> add(Long userId, Long productId, Integer count) {
        Cart cart = cartMapper.selectCartByUserIdProductId(userId, productId);
        if (cart == null) {
            //这个产品不在购物车中，需要重新创建一个cart对象
            Cart cartItem = new Cart();
            cartItem.setQuantity(count);
            cartItem.setProductId(productId);
            cartItem.setUserId(userId);
            cartItem.setChecked(Constant.Cart.CHECKED);
            cartMapper.insert(cartItem);
        } else {
            //产品已经在购物车
            count = cart.getQuantity() + count;
            cart.setChecked(count);
            cartMapper.updateByPrimaryKey(cart);
        }
        CartVo cartVo = getCartVoLimit(userId);
        //将新添加的数据存入redis中
        String redisKey = "cart_" + userId + "_";
        String redisValue = GsonConvertUtil.convertObjectToJSON(cartVo);
        jedisClient.set(redisKey, redisValue);
        return ServerResponse.createBySuccess(cartVo);
    }

    @Override
    public ServerResponse<CartVo> update(Long userId, Long productId, Integer count) {
        Cart cart = cartMapper.selectCartByUserIdProductId(userId, productId);
        if (cart != null) {
            cart.setQuantity(count);
        }
        cartMapper.updateByPrimaryKeySelective(cart);

        CartVo cartVo = getCartVoLimit(userId);
        //将更新的数据更新到redis中
        String redisKey = "cart_" + userId + "_";
        String redisValue = GsonConvertUtil.convertObjectToJSON(cartVo);
        jedisClient.set(redisKey, redisValue);
        return ServerResponse.createBySuccess(cartVo);
    }

    @Override
    public ServerResponse<CartVo> deleteProduct(Long userId, String productIds) {
        List<String> productList = Splitter.on(",").splitToList(productIds);
        if (productList.size() > 0) {
            cartMapper.deleteByUserIdProductId(userId, productList);
        }
        return null;
    }


    @Override
    public ServerResponse<CartVo> list(Long userId) {
        CartVo cartVo = null;
        String redisKey = "cart_" + userId + "_";
        String cartVoStr = jedisClient.get(redisKey);
        if (StringUtils.isBlank(cartVoStr)) {
            cartVo = getCartVoLimit(userId);
            jedisClient.set(redisKey, GsonConvertUtil.convertObjectToJSON(cartVo));
        } else {
            cartVo = GsonConvertUtil.convertJSONToObject(cartVoStr, CartVo.class);

        }
        if (cartVo == null) {
            return ServerResponse.createByErrorMessage("未找到数据");
        }
        return ServerResponse.createBySuccess(cartVo);
    }

    @Override
    public ServerResponse<CartVo> selectOrUnSelect(Long userId, Long productId, Integer checked) {
        cartMapper.checkedOrUncheckedProduct(userId, productId, checked);
        return this.list(userId);
    }

    @Override
    public ServerResponse<Integer> getCartProductCount(Long userId) {
        if (userId == null) {
            return ServerResponse.createBySuccess(0);
        }
        return ServerResponse
                .createBySuccess(cartMapper.selectCartProductCount(userId));
    }

    /**
     * 获取商品在购物车中被限制的数量
     *
     * @param userId
     * @return
     */
    private CartVo getCartVoLimit(Long userId) {
        CartVo cartVo = new CartVo();
        List<Cart> cartList = cartMapper.getCartByUserId(userId);
        List<CartProductVo> cartProductVoList = Lists.newArrayList();

        BigDecimal totalprice = new BigDecimal("0");

        if (cartList != null && cartList.size() > 0) {
            for (Cart cartItem : cartList) {
                CartProductVo cartProductVo = new CartProductVo();
                cartProductVo.setId(cartItem.getId());
                cartProductVo.setUserId(userId);
                cartProductVo.setProductId(cartItem.getProductId());

                Product product = productMapper.selectByPrimaryKey(cartItem.getProductId());
                if (product != null) {
                    cartProductVo.setProductMainImage(product.getMainImage());
                    cartProductVo.setProductName(product.getName());
                    cartProductVo.setProductSubtitle(product.getSubtitle());
                    cartProductVo.setProductStatus(product.getStatus());
                    cartProductVo.setProductPrice(product.getPrice());
                    cartProductVo.setProductStock(product.getStock());
                    //判断库存
                    int buyLimitCount = 0;
                    if (product.getStock() >= cartItem.getQuantity()) {
                        buyLimitCount = cartItem.getQuantity();
                        cartProductVo.setLimitQuantity(Constant.Cart.LIMIT_NUM_SUCCESS);
                    } else {
                        buyLimitCount = product.getStock();
                        cartProductVo.setLimitQuantity(Constant.Cart.LIMIT_NUM_FAIL);
                        //购物车中更新有效库存
                        Cart cartForQuantity = new Cart();
                        cartForQuantity.setId(cartItem.getId());
                        cartForQuantity.setQuantity(buyLimitCount);
                        cartMapper.updateByPrimaryKeySelective(cartForQuantity);
                    }

                    cartProductVo.setQuantity(buyLimitCount);
                    //计算商品总价
                    cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(),
                            cartProductVo.getQuantity()));

                    cartProductVo.setProductChecked(cartItem.getChecked());
                }

                if (cartItem.getChecked() == Constant.Cart.CHECKED) {
                    //如果勾选了就计算到购物车总价中
                    totalprice = BigDecimalUtil.add(totalprice.doubleValue(),
                            cartProductVo.getProductTotalPrice().doubleValue());
                }
                cartProductVoList.add(cartProductVo);
            }
        }
        cartVo.setCartTotalPrice(totalprice);
        cartVo.setCartProductVoList(cartProductVoList);
        cartVo.setAllChecked(getAllCheckStatus(userId));
        cartVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        return cartVo;

    }

    private boolean getAllCheckStatus(Long userId) {
        return userId != null && cartMapper.selectCartProductCheckedByUserId(userId) == 0;
    }

}
