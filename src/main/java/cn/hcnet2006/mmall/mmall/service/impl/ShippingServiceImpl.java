package cn.hcnet2006.mmall.mmall.service.impl;

import cn.hcnet2006.mmall.mmall.bean.MmallShipping;
import cn.hcnet2006.mmall.mmall.common.ServerResponse;
import cn.hcnet2006.mmall.mmall.mapper.MmallShippingMapper;
import cn.hcnet2006.mmall.mmall.service.IShippingService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;

import java.util.List;
import java.util.Map;

/**
 * 收获地址实现类
 */
@Service
public class ShippingServiceImpl implements IShippingService {
    @Autowired
    private MmallShippingMapper shippingMapper;
    @Override
    public int save(MmallShipping record) {
        return 0;
    }

    @Override
    public int delete(MmallShipping record) {
        return 0;
    }

    @Override
    public int delete(List<MmallShipping> records) {
        return 0;
    }

    @Override
    public MmallShipping findById(Long id) {
        return null;
    }

    @Override
    public int update(MmallShipping record) {
        return 0;
    }

    @Override
    public int update(List<MmallShipping> records) {
        return 0;
    }

    /**
     * 新增收货地址
     * @param userId
     * @param shipping
     * @return
     */
    @Override
    public ServerResponse add(Integer userId, MmallShipping shipping) {
        shipping.setUserId(userId);
        int rowCount = shippingMapper.insert(shipping);
        if (rowCount > 0){
            Map<String, Object> result = Maps.newHashMap();
            result.put("ShppingId",shipping.getId());
            return ServerResponse.createBySuccess(result);
        }
        return ServerResponse.createByError("收货地址增加失败");
    }

    /**
     * 删除收货地址
     * @param userId
     * @param shippingId
     * @return
     */
    @Override
    public ServerResponse<String> delete(Integer userId, Integer shippingId) {
        int resultCount = shippingMapper.deleteByUserIdShippingId(userId,shippingId);
        if (resultCount > 0){
            return ServerResponse.createBySuccess("删除成功");
        }
        return ServerResponse.createByError("删除失败");
    }

    /**
     * 修改收货地址
     * @param userId
     * @param shipping
     * @return
     */
    @Override
    public ServerResponse<String> update(Integer userId, MmallShipping shipping) {
        shipping.setUserId(userId);
        int resultCount = shippingMapper.updateByPrimaryKey(shipping);
        if (resultCount > 0){
            return ServerResponse.createBySuccess("修改成功");
        }
        return ServerResponse.createByError("修改失败");
    }

    /**
     * 查找收货地址
     * @param userId
     * @param shippingId
     * @return
     */
    @Override
    public ServerResponse<MmallShipping> select(Integer userId , Integer shippingId){

        MmallShipping shipping = shippingMapper.selectByUserIdShippingId(userId, shippingId);
        if (shipping != null){
            return ServerResponse.createBySuccess("查询地址成功",shipping);
        }
        return ServerResponse.createByError("查询地址失败");
    }

    /**
     * 返回分页列表
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<MmallShipping> shippingList = shippingMapper.selectByUserId(userId);
        PageInfo pageInfo = new PageInfo(shippingList);
        return ServerResponse.createBySuccess(pageInfo);
    }
}
