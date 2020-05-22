package cn.hcnet2006.mmall.mmall.vo;

import cn.hcnet2006.mmall.mmall.bean.MmallCart;
import cn.hcnet2006.mmall.mmall.bean.MmallProduct;

import java.math.BigDecimal;

/**
 * 购物车中某一件商品的VO封装,结合了产品和购物车的抽象对象
 */
public class CartProductVo {
    //购物车记录ID
    private Integer id;
    //用户ID
    private Integer userId;
    //产品ID
    private Integer productId;
    //购物车中商品的数量
    private Integer quantity;

    //商品名
    private String productName;
    //商品副标题
    private String productSubtitle;
    //商品主图
    private String productMainImage;
    //商品价格
    private BigDecimal productPrice;
    //商品状态
    private Integer productStatus;
    //商品选中的状态
    private Integer productChecked;
    //商品库存
    private Integer productStock;
    //商品总价格
    private BigDecimal productTotalPrice;
    //限制数量的一个返回结果
    private String limitQuantity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSubtitle() {
        return productSubtitle;
    }

    public void setProductSubtitle(String productSubtitle) {
        this.productSubtitle = productSubtitle;
    }

    public String getProductMainImage() {
        return productMainImage;
    }

    public void setProductMainImage(String productMainImage) {
        this.productMainImage = productMainImage;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Integer productStatus) {
        this.productStatus = productStatus;
    }

    public Integer getProductChecked() {
        return productChecked;
    }

    public void setProductChecked(Integer productChecked) {
        this.productChecked = productChecked;
    }

    public Integer getProductStock() {
        return productStock;
    }

    public void setProductStock(Integer productStock) {
        this.productStock = productStock;
    }

    public BigDecimal getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(BigDecimal productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    public String getLimitQuantity() {
        return limitQuantity;
    }

    public void setLimitQuantity(String limitQuantity) {
        this.limitQuantity = limitQuantity;
    }
}
