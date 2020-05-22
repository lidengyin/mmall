package cn.hcnet2006.mmall.mmall.service;

import cn.hcnet2006.mmall.mmall.bean.MmallProduct;
import cn.hcnet2006.mmall.mmall.common.ServerResponse;
import cn.hcnet2006.mmall.mmall.vo.ProductDetailVo;
import com.github.pagehelper.PageInfo;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface IProductService extends CurdService<MmallProduct> {
    @Override
    int save(MmallProduct record);

    @Override
    int delete(MmallProduct record);

    @Override
    int delete(List<MmallProduct> records);

    @Override
    MmallProduct findById(Long id);

    @Override
    int update(MmallProduct record);

    @Override
    int update(List<MmallProduct> records);

    /**
     * 保存或者更新产品
     * @param product
     * @return
     */
    public ServerResponse saveOrUpdateProduct(MmallProduct product);

    /**
     * 修改产品上下架状态
     * @param productId
     * @param status
     * @return
     */
    public ServerResponse<String> setSaleStatus(Integer productId, Integer status);

    /**
     * 获取商品详情
     * @param productId
     * @return
     */
    public ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);

    /**
     * 获取商品列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    public ServerResponse<PageInfo> getProductList(int pageNum, int pageSize);

    /**
     * 前台商品搜索
     * @param productName
     * @param productId
     * @param pageNum
     * @param pageSize
     * @return
     */
    public ServerResponse<PageInfo> searchProduct(String productName, Integer productId, int pageNum, int pageSize);

    /**
     * 前台商品详情
     * @param productId
     * @return
     */
    public ServerResponse<ProductDetailVo> getProductDetail(Integer productId);

    /**
     * 根据关键字和分类分页获取商品列表
     * @param keyword
     * @param categoryId
     * @param pageNum
     * @param pageSize
     * @return
     */
    public ServerResponse<PageInfo> getProductByKeywordCategory(String keyword,  Integer categoryId
            , String orderBy, int pageNum, int pageSize);


}
