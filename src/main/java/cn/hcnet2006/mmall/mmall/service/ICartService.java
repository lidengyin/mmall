package cn.hcnet2006.mmall.mmall.service;

import cn.hcnet2006.mmall.mmall.common.ServerResponse;
import cn.hcnet2006.mmall.mmall.vo.CartVo;

/**
 * 购物车模块
 */
public interface ICartService {

    /**
     * 添加购物车商品
     * @param userId
     * @param productId
     * @param count
     * @return
     */
    public ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count);

    /**
     * 修改购物车记录
     * @param userId
     * @param productId
     * @param count
     * @return
     */
    public ServerResponse<CartVo> update(Integer userId, Integer productId, Integer count);

    /**
     * 删除购物车记录
     * @param userId
     * @param productIds
     * @return
     */
    public ServerResponse<CartVo> deleteProduct(Integer userId, String productIds);

    /**
     * 查询购物车列表
     * @param userId
     * @return
     */
    public ServerResponse<CartVo> list(Integer userId);

    /**
     * 全选或者全反选
     * @param userId
     * @return
     */
    public ServerResponse<CartVo> selectOrUnSelect(Integer userId,Integer productId, Integer checked);

    /**
     * 获取购物车中产品的数量
     * @param userId
     * @return
     */
    public ServerResponse<Integer> getCartProductCount(Integer userId);


}
