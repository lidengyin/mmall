package cn.hcnet2006.mmall.mmall.mapper;

import cn.hcnet2006.mmall.mmall.bean.MmallProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface MmallProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MmallProduct record);

    MmallProduct selectByPrimaryKey(Integer id);

    List<MmallProduct> selectAll();

    int updateByPrimaryKey(MmallProduct record);

    int updateByPrimaryKeySelective(MmallProduct product);

    /**
     * 根据ID查询或者根据用户名模糊查询
     * @param productName
     * @param productId
     * @return
     */
    List<MmallProduct> selectByNameAndId(@Param("productName") String productName, @Param("productId") Integer productId);

    /**
     * 根据名称和品类ID获取商品列表
     * @param productName
     * @param categoryIdList
     * @return
     */
    List<MmallProduct> selectByNameAndCategoryIds(@Param("productName") String productName,@Param("categoryIdList") List<Integer> categoryIdList);
}