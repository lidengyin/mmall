package cn.hcnet2006.mmall.mmall.mapper;

import cn.hcnet2006.mmall.mmall.bean.MmallPayInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface MmallPayInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MmallPayInfo record);

    MmallPayInfo selectByPrimaryKey(Integer id);

    List<MmallPayInfo> selectAll();

    int updateByPrimaryKey(MmallPayInfo record);
}