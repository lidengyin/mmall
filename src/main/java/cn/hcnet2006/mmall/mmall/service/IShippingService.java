package cn.hcnet2006.mmall.mmall.service;

import cn.hcnet2006.mmall.mmall.bean.MmallShipping;
import cn.hcnet2006.mmall.mmall.common.ServerResponse;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface IShippingService extends CurdService<MmallShipping>{
    @Override
    int save(MmallShipping record);

    @Override
    int delete(MmallShipping record);

    @Override
    int delete(List<MmallShipping> records);

    @Override
    MmallShipping findById(Long id);

    @Override
    int update(MmallShipping record);

    @Override
    int update(List<MmallShipping> records);

    /**
     * 新建收货地址
     * @param userId
     * @param shipping
     * @return
     */
    ServerResponse add(Integer userId, MmallShipping shipping);

    ServerResponse<String> delete(Integer userId, Integer shippingId);

    ServerResponse<String> update(Integer userId, MmallShipping shipping);

    ServerResponse<MmallShipping> select(Integer userId , Integer shippingId);
    ServerResponse<PageInfo> list(Integer userId,int pageNum, int pageSize);
}
