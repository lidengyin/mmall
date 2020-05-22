package cn.hcnet2006.mmall.mmall.mapper;

import cn.hcnet2006.mmall.mmall.bean.MmallShipping;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;

import java.util.List;
@Mapper
public interface MmallShippingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MmallShipping record);

    MmallShipping selectByPrimaryKey(Integer id);

    List<MmallShipping> selectAll();

    int updateByPrimaryKey(MmallShipping record);

    int deleteByUserIdShippingId(Integer userId, Integer shippingId);

    int updateByPrimaryKeySelective(MmallShipping record);

    MmallShipping selectByUserIdShippingId(Integer userId, Integer shippingId);

    List<MmallShipping> selectByUserId(Integer userId);

}