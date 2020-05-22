package cn.hcnet2006.mmall.mmall.mapper;

import cn.hcnet2006.mmall.mmall.bean.MmallCart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;

import java.util.List;
@Mapper
public interface MmallCartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MmallCart record);

    MmallCart selectByPrimaryKey(Integer id);

    List<MmallCart> selectAll();

    int updateByPrimaryKey(MmallCart record);

    int updateByPrimaryKeySelective(MmallCart record);

    /**
     * 根据用户ID和产品ID判断是否购物车记录存在
     * @param userId
     * @param productId
     * @return
     */
    MmallCart selectCartByUserIdProductId(Integer userId, Integer productId);

    /**
     * 根据用户ID查询购物车
     * @param userId
     * @return
     */
    List<MmallCart> selectCartByUserId(Integer userId);

    /**
     * 查询是否有未被勾选明细
     * @param userId
     * @return
     */
    int selectCartProductCheckedStatusByUserId(Integer userId);

    /**
     * 根据用户ID和产品ID列表批量删除
     * @param userId
     * @param productIdList
     * @return
     */
    int deleteByUserIdProductIds(@Param("userId") Integer userId, @Param("productIdList") List<String> productIdList);

    /**
     * 全选或者全反选某用户购物车全部商品
     * @param userId
     * @return
     */
    int checkOrUnCheckProduct(Integer userId, Integer productId, Integer checked);

    /**
     * 获取购物车中产品的数量
     * @param userId
     * @return
     */
    int selectCartProductCount(Integer userId);

    List<MmallCart> selectCheckedByUserId(Integer userId);
}