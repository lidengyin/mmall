package cn.hcnet2006.mmall.mmall.service.impl;

import cn.hcnet2006.mmall.mmall.bean.MmallCategory;
import cn.hcnet2006.mmall.mmall.bean.MmallProduct;
import cn.hcnet2006.mmall.mmall.common.Const;
import cn.hcnet2006.mmall.mmall.common.ResponseCode;
import cn.hcnet2006.mmall.mmall.common.ServerResponse;
import cn.hcnet2006.mmall.mmall.mapper.MmallCategoryMapper;
import cn.hcnet2006.mmall.mmall.mapper.MmallProductMapper;
import cn.hcnet2006.mmall.mmall.service.ICategoryService;
import cn.hcnet2006.mmall.mmall.service.IProductService;
import cn.hcnet2006.mmall.mmall.util.DateTimeUtil;
import cn.hcnet2006.mmall.mmall.util.PropertiesUtil;
import cn.hcnet2006.mmall.mmall.vo.ProductDetailVo;
import cn.hcnet2006.mmall.mmall.vo.ProductListVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.catalina.Server;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private MmallProductMapper productMapper;
    @Autowired
    private MmallCategoryMapper categoryMapper;
    @Autowired
    private ICategoryService categoryService;

    private static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    @Override
    public int save(MmallProduct record) {
        return 0;
    }

    @Override
    public int delete(MmallProduct record) {
        return 0;
    }

    @Override
    public int delete(List<MmallProduct> records) {
        return 0;
    }

    @Override
    public MmallProduct findById(Long id) {
        return null;
    }

    @Override
    public int update(MmallProduct record) {
        return 0;
    }

    @Override
    public int update(List<MmallProduct> records) {
        return 0;
    }

    /**
     * 更新或保存产品
     * @param product
     * @return
     */
    @Override
    public ServerResponse saveOrUpdateProduct(MmallProduct product) {
        //判断参数是否为空
        if (product != null){
            //判断子图是否为空
            if (StringUtils.isNotBlank(product.getSubImages())){
                //选取第一章子图作为主图显示
                String[] subImageArray = product.getSubImages().split(",");
                if (subImageArray.length > 0){
                    product.setMainImage(subImageArray[0]);
                }
            }
            //判断是更新还是新增
            if (product.getId() != null){
                int rowCount = productMapper.updateByPrimaryKeySelective(product);
                if (rowCount > 0){
                    return ServerResponse.createBySuccess("产品更新成功,编号是"+product.getId());
                }
                return ServerResponse.createByError("产品更新失败");
            }else{
                int rowCount = productMapper.insert(product);
                if (rowCount > 0){
                    return ServerResponse.createBySuccess("产品新增成功,编号是"+product.getId());
                }
                return ServerResponse.createByError("产品新增失败");
            }
        }
        return ServerResponse.createByError("新增或更新商品失败");
    }

    /**
     * 设置产品上下架状态
     * @param productId
     * @param status
     * @return
     */
    @Override
    public ServerResponse<String> setSaleStatus(Integer productId, Integer status) {
        //判断参数是否错误
        if (productId == null || status == null){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        //判断产品修改是否成功
        MmallProduct product = new MmallProduct();
        product.setId(productId);
        product.setStatus(status);
        int rowCount = productMapper.updateByPrimaryKeySelective(product);
        if (rowCount > 0){
            return ServerResponse.createBySuccess("产品销售状态修改成功");
        }
        return ServerResponse.createByError("产品销售状态修改失败");
    }

    /**
     * 获取商品详细信息
     * @param productId
     * @return
     */
    @Override
    public ServerResponse<ProductDetailVo> manageProductDetail(Integer productId) {
        //判断参数是否错误
        if (productId == null){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        //判断商品是否存在
        MmallProduct product = productMapper.selectByPrimaryKey(productId);
        if ((product == null)){
            return ServerResponse.createByError("产品已经下架或者删除");
        }
        //VO对象－－value Object
        //pojo-bo(buiness object)--vo(view object)
        ProductDetailVo productDetailVo = assembleProductDetailVo(product);
        return ServerResponse.createBySuccess(productDetailVo);
    }

    /**
     * 分页返回商品列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ServerResponse<PageInfo> getProductList(int pageNum, int pageSize) {
        //satrtPage--start
        //填充自己的sql查询逻辑
        //pageHelper收尾
        PageHelper.startPage(pageNum,pageSize);
        List<ProductListVo> productListVoList = new ArrayList<>();
        List<MmallProduct> productList = productMapper.selectAll();
        for (MmallProduct productItem : productList){
            ProductListVo productListVo = assembleProductListVo(productItem);
            productListVoList.add(productListVo);

        }
        PageInfo pageInfo = new PageInfo(productList);
        pageInfo.setList(productListVoList);
        return ServerResponse.createBySuccess(pageInfo);

    }

    /**
     * 分页返回商品搜索结果
     * @param productName
     * @param productId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ServerResponse<PageInfo> searchProduct(String productName, Integer productId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        //设置产品名称为模糊查询
        if (StringUtils.isNotBlank(productName)){
            productName = new StringBuilder().append("%").append(productName).append("%").toString();
        }
        logger.info("商品名称"+productName);
        List<MmallProduct> productList = productMapper.selectByNameAndId(productName, productId);
        List<ProductListVo> productListVoList = new ArrayList<>();
        //设置返回为VO对象
        for (MmallProduct productItem : productList){
            ProductListVo productListVo =assembleProductListVo(productItem);
            productListVoList.add(productListVo);
        }
        PageInfo pageInfo = new PageInfo(productList);
        pageInfo.setList(productListVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    /**
     * 前台获取产品详情
     * @param productId
     * @return
     */
    @Override
    public ServerResponse<ProductDetailVo> getProductDetail(Integer productId) {
        //判断参数是否错误
        if (productId == null){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        //判断商品是否存在
        MmallProduct product = productMapper.selectByPrimaryKey(productId);
        if ((product == null)){
            return ServerResponse.createByError("产品已经下架或者删除");
        }
        //判断商品是否下架
        if (product.getStatus() != Const.ProductStatusEnum.ON_SALE.getCode()){
            return ServerResponse.createByError("商品已经下架");
        }
        //VO对象－－value Object
        //pojo-bo(buiness object)--vo(view object)
        ProductDetailVo productDetailVo = assembleProductDetailVo(product);
        return ServerResponse.createBySuccess(productDetailVo);
    }

    @Override
    public ServerResponse<PageInfo> getProductByKeywordCategory(String keyword,  Integer categoryId, String orderBy,int pageNum, int pageSize) {
        //判断参数是否存在
        if (StringUtils.isBlank(keyword) && categoryId == null){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());

        }
        List<Integer> categoryIdList = Lists.newArrayList();
        //判断是否需要分类查询
        if (categoryId != null){
            //判断检索结果是否为空
            MmallCategory category = categoryMapper.selectByPrimaryKey(categoryId);
            if (category == null && StringUtils.isBlank(keyword)){

                PageHelper.startPage(pageNum,pageSize);
                List<ProductListVo> productListVoList = Lists.newArrayList();
                PageInfo pageInfo = new PageInfo(productListVoList);
                return ServerResponse.createBySuccess(pageInfo);
            }
            ServerResponse<List<Integer>> response = categoryService.selectCategoryChildrenById(categoryId);
            categoryIdList =  response.getData();
        }
        //判断是否需要关键字查询
        if (StringUtils.isNotBlank(keyword)){
            keyword = new StringBuilder().append("%").append(keyword).append("%").toString();
        }
        PageHelper.startPage(pageNum,pageSize);
        //判断是否需要排序处理
        if (StringUtils.isNotBlank(orderBy)){
            //判断排序参数是否正确
            if (Const.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)){
                String[] orderByArray = orderBy.split("_");
                PageHelper.orderBy(orderByArray[0]+" "+orderByArray[1]);

            }
        }
        //获得产品列表
        List<MmallProduct> productList = productMapper.selectByNameAndCategoryIds(StringUtils.isBlank(keyword)?null:keyword, categoryIdList.size() == 0?null:categoryIdList);

        List<ProductListVo> productListVoList = Lists.newArrayList();
        for (MmallProduct product: productList){
            ProductListVo productListVo =assembleProductListVo(product);
            productListVoList.add(productListVo);
        }

        PageInfo pageInfo = new PageInfo(productList);
        pageInfo.setList(productListVoList);
        return ServerResponse.createBySuccess(pageInfo);


    }

    //VO对象封装
    private ProductListVo assembleProductListVo(MmallProduct product){


        ProductListVo productListVo = new ProductListVo();
        productListVo.setId(product.getId());
        productListVo.setName(product.getName());
        productListVo.setMainImage(product.getMainImage());
        productListVo.setCategoryId(product.getCategoryId());
        productListVo.setPrice(product.getPrice());
        productListVo.setStatus(product.getStatus());
        productListVo.setSubtitle(product.getSubtitle());
        productListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","ftp://121.36.145.230/"));
        return productListVo;
    }


    //VO对象组装
    private ProductDetailVo assembleProductDetailVo(MmallProduct product){

        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setName(product.getName());
        productDetailVo.setId(product.getId());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setSubImages(product.getSubImages());
        productDetailVo.setStock(product.getStock());
        productDetailVo.setStatus(product.getStatus());
        //图片服务器地址
        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://121.36.145.230/"));

        //父类ID
        //判断品类根节点是否存在
        MmallCategory category = categoryMapper.selectByPrimaryKey(product.getId());
        //如果为空设置默认父节点为０，即最高节点
        if (category == null){
            productDetailVo.setParentCategoryId(0);
        }else{
            productDetailVo.setParentCategoryId(category.getParentId());
        }
        //创建时间
        productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        //修改时间
        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
        return productDetailVo;
    }

}
