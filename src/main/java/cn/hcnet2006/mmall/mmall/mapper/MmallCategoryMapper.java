package cn.hcnet2006.mmall.mmall.mapper;

import cn.hcnet2006.mmall.mmall.bean.MmallCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface MmallCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MmallCategory record);

    MmallCategory selectByPrimaryKey(Integer id);

    List<MmallCategory> selectAll();

    int updateByPrimaryKey(MmallCategory record);
    int updateByPrimaryKeySelective(MmallCategory record);

    /**
     * 根据父类ID获取子类
     * @param parentId
     * @return
     */
    List<MmallCategory> selectCategoryChildrenByParentId(Integer parentId);

    /**
     * 检查分类名是否已经被注册
     * @param categoryName
     * @return
     */
    int checkCategoryName(String categoryName);

    /**
     * 检查父类ID是否存在，除去０
     * @param parentId
     * @return
     */
    int checkParentId(Integer parentId);


}