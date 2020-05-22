package cn.hcnet2006.mmall.mmall.mapper;

import cn.hcnet2006.mmall.mmall.bean.MmallOrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface MmallOrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MmallOrderItem record);

    MmallOrderItem selectByPrimaryKey(Integer id);

    List<MmallOrderItem> selectAll();

    int updateByPrimaryKey(MmallOrderItem record);

    List<MmallOrderItem> getByOrderNoUserId(Long orderNo, Integer userId);

    /**
     * MyBatis批量插入
     * @param orderItemList
     */
    void batchInsert(@Param("orderItemList") List<MmallOrderItem> orderItemList);

    List<MmallOrderItem> selectByOrderNo(Long orderNo);


}