package cn.hcnet2006.mmall.mmall.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品列表VO对象
 */
public class ProductListVo {
    private Integer id;
    //分类ID
    private Integer categoryId;
    //商品名
    private String name;
    //商品副标题
    private String subtitle;
    //商品主图
    private String mainImage;
    //商品价格
    private BigDecimal price;
   //商品状态
    private Integer status;
    //图片服务器前缀
    private String imageHost;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }
}
