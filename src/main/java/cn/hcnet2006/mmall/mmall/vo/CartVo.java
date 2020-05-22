package cn.hcnet2006.mmall.mmall.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 购物车明细集合
 */
public class CartVo {
    //购物车商品列表
    private List<CartProductVo> cartProductVoList;
    //购物车商品总价格
    private BigDecimal cartTotalPrice;
    //是否已经都被勾选
    private Boolean allChecked;
    //图片服务器地址
    private String imageHost;

    public List<CartProductVo> getCartProductVoList() {
        return cartProductVoList;
    }

    public void setCartProductVoList(List<CartProductVo> cartProductVoList) {
        this.cartProductVoList = cartProductVoList;
    }

    public BigDecimal getCartTotalPrice() {
        return cartTotalPrice;
    }

    public void setCartTotalPrice(BigDecimal cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
    }

    public Boolean getAllChecked() {
        return allChecked;
    }

    public void setAllChecked(Boolean allChecked) {
        this.allChecked = allChecked;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }
}
