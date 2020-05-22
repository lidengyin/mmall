package cn.hcnet2006.mmall.mmall.mapper;

import cn.hcnet2006.mmall.mmall.bean.MmallOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface MmallOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MmallOrder record);

    MmallOrder selectByPrimaryKey(Integer id);

    List<MmallOrder> selectAll();

    int updateByPrimaryKey(MmallOrder record);

    MmallOrder selectOrderByUserIdAndOrderNo(Integer userId,Long orderNo );
    MmallOrder selectByOrderNo(Long orderNo);
    int updateByPrimaryKeySelective(MmallOrder order);

    List<MmallOrder> selectByUserId(Integer userId);
}