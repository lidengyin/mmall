package cn.hcnet2006.mmall.mmall.service;

import cn.hcnet2006.mmall.mmall.bean.MmallOrder;
import cn.hcnet2006.mmall.mmall.common.ServerResponse;
import cn.hcnet2006.mmall.mmall.vo.OrderVo;
import com.github.pagehelper.PageInfo;
import java.util.Map;

public interface IOrderService extends CurdService<MmallOrder> {

    /**
     *
     * @param orderNo　订单编号
     * @param userId　用户编号
     * @param path　二维码路径
     * @return
     */
    public ServerResponse pay(Long orderNo, Integer userId, String path);

    /**
     * 支付回调更新
     * @param params
     * @return
     */
    public ServerResponse alipayCallback(Map<String, String> params);

    /**
     * 支付后．前端判断支付状态
     * @param userId
     * @param orderNo
     * @return
     */
    public ServerResponse queryOrderPayStatys(Integer userId, Long orderNo);

    /**
     * 创建订单
     * @param userId
     * @param shippingId
     * @return
     */
    public ServerResponse<Object> createOrder(Integer userId, Integer shippingId);


    /**
     * 取消订单那
     * @param userId
     * @param orderNo
     * @return
     */
    public ServerResponse<String> cancelOrder(Integer userId, Long orderNo);

    /**
     * 获取订单可以预览购物车商品
     * @param userId
     * @return
     */
    public ServerResponse getOrderCartProduct(Integer userId);

    /**
     * 获取订单详情
     * @param userId
     * @param orderNo
     * @return
     */
    public ServerResponse<OrderVo> detail(Integer userId, Long orderNo);

    /**
     * 个人中心获取订单列表
     * @param userId
     * @return
     */
    public ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize);

    /**
     * 管理员查看订单列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    public ServerResponse<PageInfo> manageList(int pageNum, int pageSize);

    /**
     * 管理员查看订单详情
     * @param orderNo
     * @return
     */
    public ServerResponse<OrderVo> manageDetail(Long orderNo);

    /**
     * 管理员根据订单号搜索
     * @param orderNo
     * @return
     */
    public ServerResponse<PageInfo> manageSearch(Long orderNo,int pageNum, int pageSize);

    /**
     * 管理员发货
     * @param orderNo
     * @return
     */
    public ServerResponse<String> manageSendGoods(Long orderNo);
}
