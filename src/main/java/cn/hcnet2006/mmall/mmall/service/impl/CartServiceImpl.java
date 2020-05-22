package cn.hcnet2006.mmall.mmall.service.impl;

import cn.hcnet2006.mmall.mmall.bean.MmallCart;
import cn.hcnet2006.mmall.mmall.bean.MmallProduct;
import cn.hcnet2006.mmall.mmall.common.Const;
import cn.hcnet2006.mmall.mmall.common.ResponseCode;
import cn.hcnet2006.mmall.mmall.common.ServerResponse;
import cn.hcnet2006.mmall.mmall.mapper.MmallCartMapper;
import cn.hcnet2006.mmall.mmall.mapper.MmallCategoryMapper;
import cn.hcnet2006.mmall.mmall.mapper.MmallProductMapper;
import cn.hcnet2006.mmall.mmall.service.ICartService;
import cn.hcnet2006.mmall.mmall.util.BigDecimalUtil;
import cn.hcnet2006.mmall.mmall.util.PropertiesUtil;
import cn.hcnet2006.mmall.mmall.vo.CartProductVo;
import cn.hcnet2006.mmall.mmall.vo.CartVo;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private MmallCartMapper cartMapper;
    @Autowired
    private MmallProductMapper productMapper;

    private static Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);
    /**
     * 添加购物车商品
     * @param userId
     * @param productId
     * @param count
     * @return
     */
    @Override
    public ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count) {
       //判断参数是否错误
        if (productId == null || count == null){
           return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
       }
        //判断该产品记录在购物车是否存在
        MmallCart cart = cartMapper.selectCartByUserIdProductId(userId, productId);
        //如果不存在则新增一个这个产品的记录
        if (cart == null){
            MmallCart cartItem = new MmallCart();
            //设置购物车中该商品数量
            cartItem.setQuantity(count);
            cartItem.setProductId(productId);
            cartItem.setUserId(userId);
            //添加默认选中状态
            cartItem.setChecked(Const.Cart.CHECKED);
            cartMapper.insert(cartItem);
        }else{
            //如果产品已经存在则数量相加即可
            cart.setQuantity(cart.getQuantity()+count);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        return this.list(userId);

    }

    /**
     * 修改购物车记录
     * @param userId
     * @param productId
     * @param count
     * @return
     */
    @Override
    public ServerResponse<CartVo> update(Integer userId, Integer productId, Integer count) {
        MmallCart cart = cartMapper.selectCartByUserIdProductId(userId,productId);
        if (cart != null){

            cart.setQuantity(count);
            System.out.println("cartQuantity:"+cart.getQuantity());

        }
        cartMapper.updateByPrimaryKeySelective(cart);
        return this.list(userId);
    }

    /**
     * 删除购物车信息
     * @param userId
     * @param productIds
     * @return
     */
    @Override
    public ServerResponse<CartVo> deleteProduct(Integer userId, String productIds) {
        //判断参数是否错误
        //转换成集合的形式
        List<String> productIdList = Splitter.on(",").splitToList(productIds);
        if (CollectionUtils.isEmpty(productIdList)){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        cartMapper.deleteByUserIdProductIds(userId,productIdList);
        return this.list(userId);
    }

    /**
     * 查询购物车列表
     * @param userId
     * @return
     */
    @Override
    public ServerResponse<CartVo> list(Integer userId) {
        CartVo cartVo = getCartVoLimit(userId);
        return ServerResponse.createBySuccess(cartVo);
    }

    /**
     * 选择或者反选
     * @param userId
     * @param productId
     * @param checked
     * @return
     */
    @Override
    public ServerResponse<CartVo> selectOrUnSelect(Integer userId,Integer productId, Integer checked) {
        cartMapper.checkOrUnCheckProduct(userId,productId,checked);
        return this.list(userId);
    }

    /**
     * 获取购物车产品数量
     * @param userId
     * @return
     */
    @Override
    public ServerResponse<Integer> getCartProductCount(Integer userId) {
        if (userId == null){
            return ServerResponse.createBySuccess(0);
        }
        return ServerResponse.createBySuccess(cartMapper.selectCartProductCount(userId));
    }

    /**
     * 封装返回逻辑==核心方法
     * @param userId
     * @return
     */
    private CartVo getCartVoLimit(Integer userId){
        CartVo cartVo = new CartVo();
        //根据用户编号获取对应的购物车列表
        List<MmallCart> cartList = cartMapper.selectCartByUserId(userId);
        List<CartProductVo> cartProductVoList = Lists.newArrayList();

        //计算总价，使用BigDecimal避免丢失精度
        BigDecimal cartTotalPrice = new BigDecimal("0");
        if (CollectionUtils.isNotEmpty(cartList)){
            //对单个购物车记录进行封装
            for (MmallCart cartItem : cartList){
                CartProductVo cartProductVo = new CartProductVo();
                cartProductVo.setId(cartItem.getId());
                cartProductVo.setUserId(cartItem.getUserId());
                cartProductVo.setProductChecked(cartItem.getChecked());
                //判断购物车记录中的商品是否为空
                MmallProduct product = productMapper.selectByPrimaryKey(cartItem.getProductId());
                //不为空对有关商品的属性进行填充
                if (product != null){
                    cartProductVo.setProductId(product.getId());
                    cartProductVo.setProductMainImage(product.getMainImage());
                    cartProductVo.setProductSubtitle(product.getSubtitle());
                    cartProductVo.setProductPrice(product.getPrice());
                    cartProductVo.setProductName(product.getName());
                    cartProductVo.setProductStatus(product.getStatus());

                    cartProductVo.setProductStock(product.getStock());
                    //判断库存是否大于购买数量
                    int buyLimitCount = 0;
                    if (product.getStock() >= cartItem.getQuantity()){
                        buyLimitCount = cartItem.getQuantity();
                        //设置最大购买数量
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);

                    }else{
                        //购买数量超出库存，在购物车中更新有效库存
                        buyLimitCount = product.getStock();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
                        //购物车中更新有效库存
                        MmallCart cartForQuantity = new MmallCart();
                        cartForQuantity.setQuantity(buyLimitCount);
                        cartForQuantity.setId(cartItem.getId());
                        cartMapper.updateByPrimaryKeySelective(cartForQuantity);

                    }
                    //购买数量
                    cartProductVo.setQuantity(buyLimitCount);

                    //计算该商品在购物车记录中的总价
                    cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(), cartItem.getQuantity().doubleValue()));
//                    cartProductVo.setProductStatus(cartItem.getChecked());
                }else{
                    continue;
                }
                //根据商品勾选状态判断是否计入购物车总价
                if (cartItem.getChecked()==Const.Cart.CHECKED){
                    //计算购物车所有商品记录总价格
                    logger.info("cartTotalPrice.doubleValue:"+cartTotalPrice.doubleValue());
                    logger.info("cartProductVo.getProductTotalPrice():"+cartProductVo.getProductTotalPrice());
                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(),cartProductVo.getProductTotalPrice().doubleValue());

                }
                cartProductVoList.add(cartProductVo);


            }
            //对于购物车整体结构进行封装
            cartVo.setCartProductVoList(cartProductVoList);
            cartVo.setCartTotalPrice(cartTotalPrice);
            cartVo.setImageHost(PropertiesUtil.getProperty("ftp.server.ip"));
            //判断是否是全选
            cartVo.setAllChecked(getAllCheckedStatus(userId));
        }
        return cartVo;
    }
    //判断是否全选逻辑封装
    private boolean getAllCheckedStatus(Integer userId){
        if (userId == null){
            return false;
        }
        //判断是否有未被勾选记录
        return cartMapper.selectCartProductCheckedStatusByUserId(userId) == 0;


    }

}
