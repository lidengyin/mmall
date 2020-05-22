package cn.hcnet2006.mmall.mmall.service.impl;

import cn.hcnet2006.mmall.mmall.bean.MmallCategory;
import cn.hcnet2006.mmall.mmall.common.ServerResponse;
import cn.hcnet2006.mmall.mmall.mapper.MmallCategoryMapper;
import cn.hcnet2006.mmall.mmall.service.ICategoryService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    private MmallCategoryMapper categoryMapper;

    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Override
    public int save(MmallCategory record) {
        return 0;
    }

    @Override
    public int delete(MmallCategory record) {
        return 0;
    }

    @Override
    public int delete(List<MmallCategory> records) {
        return 0;
    }

    @Override
    public MmallCategory findById(Long id) {
        return null;
    }

    @Override
    public int update(MmallCategory record) {
        return 0;
    }

    @Override
    public int update(List<MmallCategory> records) {
        return 0;
    }

    /**
     * 增加商品分类管理节点
     * @param categoryName
     * @param parentId
     * @return
     */
    @Override
    public ServerResponse addCategory(String categoryName, Integer parentId) {
        //判断参数是否完全
        if (parentId == null || StringUtils.isBlank(categoryName)){
            return ServerResponse.createByError("添加品类参数错误");
        }
        //判断品类名是否已经被注册
        int resultCount = categoryMapper.checkCategoryName(categoryName);
        if (resultCount > 0){
            return ServerResponse.createByError("该品类名已经被注册");
        }
        //判断父类ID是否存在
        resultCount = categoryMapper.checkParentId(parentId);
        if (resultCount == 0 && parentId != 0){
            return ServerResponse.createByError("父类ID不存在");
        }

        MmallCategory category = new MmallCategory();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);
        int rowCount = categoryMapper.insert(category);
        //判断品类是否添加成功
        if (rowCount > 0){
            return ServerResponse.createBySuccess("添加品类成功");
        }
        return ServerResponse.createByError("品类添加失败");
    }

    /**
     * 修改品类名称
     * @param categoryId
     * @param categoryName
     * @return
     */
    @Override
    public ServerResponse updateCategoryName(Integer categoryId, String categoryName) {
        //判断参数是否完全
        if (categoryId == null || StringUtils.isBlank(categoryName)){
            return ServerResponse.createByError("修改品类参数错误");
        }
        //判断品类名是否已经被注册
        int resultCount = categoryMapper.checkCategoryName(categoryName);
        if (resultCount > 0){
            return ServerResponse.createByError("该品类名已经被注册");
        }
        MmallCategory category = new MmallCategory();
        category.setName(categoryName);
        category.setId(categoryId);

        //判断修改是否成功
        int rowCount = categoryMapper.updateByPrimaryKeySelective(category);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("品类修改成功");
        }
        return ServerResponse.createByError("品类名称修改失败");
    }

    /**
     * 获取当前品类的下一级节点
     * @param categoryId
     * @return
     */
    @Override
    public ServerResponse<List<MmallCategory>> getChildrenParallelCategory(Integer categoryId) {
        //判断下一级节点集合是否为空
        List<MmallCategory> categories = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        if (CollectionUtils.isEmpty(categories)){
            logger.info("未找到当前分类的子分类");
        }
        return ServerResponse.createBySuccess(categories);
    }

    /**
     * 嵌套获取下一级品类的字节点ID
     * @param categoryId
     * @return
     */
    @Override
    public ServerResponse<List<Integer>> selectCategoryChildrenById(Integer categoryId) {
        Set<MmallCategory> categorySet = Sets.newHashSet();
        findChildCategory(categorySet,categoryId);

        List<Integer> categoryList = Lists.newArrayList();
        //遍历获取字节点ID
        if(categoryId != null){
            for (MmallCategory category : categorySet){
                categoryList.add(category.getId());
            }
        }
        return ServerResponse.createBySuccess(categoryList);
    }

    /**
     * 递归查询子节点
     * @param categorySet
     * @param categoryId
     * @return
     */
    //递归方法调用本身，使用Set重写hashCode排重
    private Set<MmallCategory> findChildCategory(Set<MmallCategory> categorySet,Integer categoryId){
        MmallCategory category = categoryMapper.selectByPrimaryKey(categoryId);


        if (category != null){
            //把子节点带入集合
            categorySet.add(category);
            //查找字节点，递归算法一定要有一个退出的条件
            List<MmallCategory> categoryList  =categoryMapper.selectCategoryChildrenByParentId(category.getId());
            //如果categoryList为空那么不会进入循环，直接跳过
            for(MmallCategory category1 : categoryList){
                if (category1 != null){
                    findChildCategory(categorySet,category1.getId());
                }
            }
        }

        return categorySet;
    }
}
