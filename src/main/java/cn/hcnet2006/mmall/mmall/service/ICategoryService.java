package cn.hcnet2006.mmall.mmall.service;

import cn.hcnet2006.mmall.mmall.bean.MmallCategory;
import cn.hcnet2006.mmall.mmall.bean.MmallProduct;
import cn.hcnet2006.mmall.mmall.common.ServerResponse;
import org.apache.catalina.Server;

import java.util.List;

public interface ICategoryService extends CurdService<MmallCategory> {
    /**
     * 品类添加
     * @param categoryName
     * @param parentId
     * @return
     */
    public ServerResponse addCategory(String categoryName, Integer parentId);

    /**
     * 品类名修改
     */
    public ServerResponse updateCategoryName(Integer categoryId, String categoryName);

    /**
     * 获取下一级品类
     * @param categoryId
     * @return
     */
    public ServerResponse<List<MmallCategory>> getChildrenParallelCategory(Integer categoryId);

    /**
     * 获取嵌套获取下一级品类的子节点
     * @param categoryId
     * @return
     */
    public ServerResponse<List<Integer>> selectCategoryChildrenById(Integer categoryId);


}
