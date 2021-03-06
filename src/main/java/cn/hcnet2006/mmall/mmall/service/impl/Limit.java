//package com.mmall.service.impl;
//
//import com.google.common.base.Splitter;
//import com.google.common.collect.Lists;
//import org.apache.commons.collections.CollectionUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.List;
//
///**
// * Created by geely
// */
//@Service("iCartService")
//public class CartServiceImpl implements ICartService {
//
//
//
//    private CartVo getCartVoLimit(Integer userId){
//        CartVo cartVo = new CartVo();
//        List<Cart> cartList = cartMapper.selectCartByUserId(userId);
//        List<CartProductVo> cartProductVoList = Lists.newArrayList();
//
//        BigDecimal cartTotalPrice = new BigDecimal("0");
//
//        if(CollectionUtils.isNotEmpty(cartList)){
//            for(Cart cartItem : cartList){
//                CartProductVo cartProductVo = new CartProductVo();
//                cartProductVo.setId(cartItem.getId());
//                cartProductVo.setUserId(userId);
//                cartProductVo.setProductId(cartItem.getProductId());
//
//                Product product = productMapper.selectByPrimaryKey(cartItem.getProductId());
//                if(product != null){
//                    cartProductVo.setProductMainImage(product.getMainImage());
//                    cartProductVo.setProductName(product.getName());
//                    cartProductVo.setProductSubtitle(product.getSubtitle());
//                    cartProductVo.setProductStatus(product.getStatus());
//                    cartProductVo.setProductPrice(product.getPrice());
//                    cartProductVo.setProductStock(product.getStock());
//                    //判断库存
//                    int buyLimitCount = 0;
//                    if(product.getStock() >= cartItem.getQuantity()){
//                        //库存充足的时候
//                        buyLimitCount = cartItem.getQuantity();
//                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
//                    }else{
//                        buyLimitCount = product.getStock();
//                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
//                        //购物车中更新有效库存
//                        Cart cartForQuantity = new Cart();
//                        cartForQuantity.setId(cartItem.getId());
//                        cartForQuantity.setQuantity(buyLimitCount);
//                        cartMapper.updateByPrimaryKeySelective(cartForQuantity);
//                    }
//                    cartProductVo.setQuantity(buyLimitCount);
//                    //计算总价
//                    cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(),cartProductVo.getQuantity()));
//                    cartProductVo.setProductChecked(cartItem.getChecked());
//                }
//
//                if(cartItem.getChecked() == Const.Cart.CHECKED){
//                    //如果已经勾选,增加到整个的购物车总价中
//                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(),cartProductVo.getProductTotalPrice().doubleValue());
//                }
//                cartProductVoList.add(cartProductVo);
//            }
//        }
//        cartVo.setCartTotalPrice(cartTotalPrice);
//        cartVo.setCartProductVoList(cartProductVoList);
//        cartVo.setAllChecked(this.getAllCheckedStatus(userId));
//        cartVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
//
//        return cartVo;
//    }
//
//    private boolean getAllCheckedStatus(Integer userId){
//        if(userId == null){
//            return false;
//        }
//        return cartMapper.selectCartProductCheckedStatusByUserId(userId) == 0;
//
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//}