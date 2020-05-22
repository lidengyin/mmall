package cn.hcnet2006.mmall.mmall.service.impl;


import cn.hcnet2006.mmall.mmall.alipay.demo.trade.utils.ZxingUtils;
import cn.hcnet2006.mmall.mmall.bean.*;
import cn.hcnet2006.mmall.mmall.common.Const;
import cn.hcnet2006.mmall.mmall.common.ServerResponse;
import cn.hcnet2006.mmall.mmall.mapper.*;
import cn.hcnet2006.mmall.mmall.service.IOrderService;
import cn.hcnet2006.mmall.mmall.util.BigDecimalUtil;

import cn.hcnet2006.mmall.mmall.util.DateTimeUtil;
import cn.hcnet2006.mmall.mmall.util.FTPUtil;
import cn.hcnet2006.mmall.mmall.util.PropertiesUtil;
import cn.hcnet2006.mmall.mmall.vo.OrderItemVo;
import cn.hcnet2006.mmall.mmall.vo.OrderProductVo;
import cn.hcnet2006.mmall.mmall.vo.OrderVo;
import cn.hcnet2006.mmall.mmall.vo.ShippingVo;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.OrderItem;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.BaseClient;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.text.resources.da.FormatData_da;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;

@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    MmallOrderMapper orderMapper;
    @Autowired
    MmallOrderItemMapper orderItemMapper;
    @Autowired
    MmallPayInfoMapper payInfoMapper;
    @Autowired
    MmallCartMapper cartMapper;
    @Autowired
    MmallProductMapper productMapper;

    @Autowired
    MmallShippingMapper shippingMapper;
    @Override
    public int save(MmallOrder record) {
        return 0;
    }

    @Override
    public int delete(MmallOrder record) {
        return 0;
    }

    @Override
    public int delete(List<MmallOrder> records) {
        return 0;
    }

    @Override
    public MmallOrder findById(Long id) {
        return null;
    }

    @Override
    public int update(MmallOrder record) {
        return 0;
    }

    @Override
    public int update(List<MmallOrder> records) {
        return 0;
    }
    /**
     *
     * @param orderNo　订单编号
     * @param userId　用户编号
     * @param path　二维码路径
     * @return
     */
    @Override
    public ServerResponse pay(Long orderNo, Integer userId, String path) {


        //存放订单编号以及二维码路径
        Map<String, String> resultMap = Maps.newHashMap();
        MmallOrder order = orderMapper.selectOrderByUserIdAndOrderNo(userId,orderNo);
        //判断订单是否存在
        if (order == null){
            return ServerResponse.createByError("哟南湖无该订单");
        }
        resultMap.put("orderNo", String.valueOf(order.getOrderNo()));
        //设置生成订单支付二维码的具体实现
        //商户订单系统中唯一订单号
        String outTradeNo = order.getOrderNo().toString();
        //订单标题
        String subject = new StringBuilder().append("军火交易扫码支付,订单号:").append(outTradeNo).toString();
        //订单总金额
        String totalAmount = order.getPayment().toString();
        //买家支付宝ID,如果该字段为空，则是默认与支付宝签约的商户的UID，也就是app对应的Pid
        //商品描述
        String body = new StringBuilder().append("订单：").append(outTradeNo).append("购买商品总金额:").append(totalAmount).toString();

        //既然使用了简单版本看了一下文档狮实在是没有扩展或者重写的版本，所以我就不动了
        try {
            // 2. 发起API调用（以支付能力下的统一收单交易创建接口为例）
            Factory.setOptions(getOptions());
            AlipayTradePrecreateResponse response = Factory.Payment.FaceToFace().preCreate (subject,
                    outTradeNo, totalAmount);
            // 3. 处理响应或异常
            //如果下单成功
            if ("10000".equals(response.code)) {
                System.out.println("调用成功:二维码流："+response.qrCode);
                //把二维码图片传递到ftp服务器，返回URL给前端
                File folder  =new File(path);
                if (!folder.exists()){
                    folder.setWritable(true);
                    folder.mkdirs();
                }
                //需要修改为运行机器上的路径
                //使用订单号作为二维码文件名
                String qrPath = String.format(path+"/qr-%s.png",response.outTradeNo);
                String qrFileName = String.format("qr-%s.png",response.outTradeNo);
                //生成二维码图片
                ZxingUtils.getQRCodeImge(response.qrCode,256,qrPath);
                //上传到FTP图派你服务器
                File targetFile = new File(path, qrFileName);
                FTPUtil.uploadFile(Lists.newArrayList(targetFile));
                //拼接二维码URL
                String qrUrl = PropertiesUtil.getProperty("ftp.server.http.prefix")+qrFileName;
                //返回
                resultMap.put("qrUrl", qrUrl);
                return ServerResponse.createBySuccess(resultMap);


            } else {
                System.err.println("调用失败，原因：" + response.msg + "，" + response.subMsg);
                return ServerResponse.createByError("调用失败，原因：" + response.msg + "，" + response.subMsg);
            }
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            return ServerResponse.createByError("调用遭遇异常，原因：" + e.getMessage());
        }
    }


    /**
     * 支付宝回调逻辑封装
     * @param params
     * @return
     */
    @Override
    public ServerResponse alipayCallback(Map<String, String> params){
        Long orderNo =  Long.parseLong(params.get("out_trade_no"));
        String tradeNo = params.get("trade_no");
        String tradeStatus = params.get("trade_status");
        //根据订单号获取订单对象
        MmallOrder order = orderMapper.selectByOrderNo(orderNo);
        if (order == null){
            return ServerResponse.createByError("非本商城订单");
        }
        //判断订单是否已经完成
        if (order.getStatus() >= Const.OrderStatusEnum.PATD.getCode()){
            return ServerResponse.createBySuccess("支付宝重复调用");
        }
        //判断是否已经支付成功
        if (Const.AlipayCallback.TRADE_STATUS_TRADE_SUCCESS.equals(tradeStatus)){
            //修改订单支付状态
            order.setPaymentTime(DateTimeUtil.strToDate(params.get("gmt_payment")));
            order.setStatus(Const.OrderStatusEnum.PATD.getCode());
            orderMapper.updateByPrimaryKeySelective(order);
        }
        //持久化支付信息
        MmallPayInfo payInfo = new MmallPayInfo();
        payInfo.setUserId(order.getUserId());
        payInfo.setOrderNo(order.getOrderNo());
        payInfo.setPayPlatform(Const.PayPlatformEnum.ALIPAY.getCode());
        payInfo.setPlatformStatus(tradeStatus);
        payInfo.setPlatformNumber(tradeNo);
        payInfoMapper.insert(payInfo);
        return ServerResponse.createBySuccess();
    }

    /**
     * 前端查询订单状态封装
     * @param userId
     * @param orderNo
     * @return
     */
    @Override
    public ServerResponse queryOrderPayStatys(Integer userId, Long orderNo){
        MmallOrder order = orderMapper.selectOrderByUserIdAndOrderNo(userId,orderNo);
        if (order == null){
            return ServerResponse.createByError("没有该订单");
        }
        if (order.getStatus() >= Const.OrderStatusEnum.PATD.getCode()){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    /**
     * 创建订单
     * @param userId
     * @param shippingId
     * @return
     */
    @Override
    public ServerResponse<Object> createOrder(Integer userId, Integer shippingId) {
        //从购物车中获取信息
        List<MmallCart> cartList = cartMapper.selectCheckedByUserId(userId);
        //获取订单明细
        ServerResponse response = getCartOrderItem(userId,cartList);
        if (!response.isSuccess()){
            return response;
        }
        List<MmallOrderItem> orderItemList = (List<MmallOrderItem>) response.getData();
        //获取订单总价格
        BigDecimal payment = getOrderTotalPrice(orderItemList);
        //订单生成
        MmallOrder order = assembleOrder(userId, shippingId, payment);
        //判断订单是否生成成功
        if (order == null){
            return ServerResponse.createByError("生成订单错误");
        }
        if (orderItemList == null){
            return ServerResponse.createByError("购物车为空");
        }
        //为订单明细添加订单号以及添加订单创建时间
        for(MmallOrderItem orderItem : orderItemList){
            orderItem.setOrderNo(order.getOrderNo());
            orderItem.setCreateTime(new Date());
        }
        //MyBatis批量插入
        orderItemMapper.batchInsert(orderItemList);
        //生成成功减少产品的库存
        reduceProductStock(orderItemList);
        //清空购物车
        this.cleanCart(cartList);
        //返回给前端数据
        OrderVo orderVo = assembleOrderVo(order,orderItemList);
        return ServerResponse.createBySuccess(orderVo);
    }

    /**
     * 取消订单
     * @param userId
     * @param orderNo
     * @return
     */
    @Override
    public ServerResponse<String> cancelOrder(Integer userId, Long orderNo) {
        MmallOrder order = orderMapper.selectOrderByUserIdAndOrderNo(userId, orderNo);
        if (order == null){
            return ServerResponse.createByError("本用户订单不存在");
        }
        if (order.getStatus() != Const.OrderStatusEnum.NO_PAY.getCode()){
            return ServerResponse.createByError("已经付款无法取消订单");
        }
        MmallOrder updateOrder = new MmallOrder();
        updateOrder.setId(order.getId());
        updateOrder.setStatus(Const.OrderStatusEnum.CABCALED.getCode());
        int rowCount = orderMapper.updateByPrimaryKeySelective(updateOrder);
        if(rowCount > 0){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    /**
     * 订单填写页面预览详情
     * @param userId
     * @return
     */
    @Override
    public ServerResponse getOrderCartProduct(Integer userId) {
        OrderProductVo orderProductVo = new OrderProductVo();
        //判断是否获取成功
        List<MmallCart> cartList = cartMapper.selectCartByUserId(userId);
        ServerResponse response = this.getCartOrderItem(userId,cartList);
        if (!response.isSuccess()){
            return response;
        }

        List<OrderItemVo> orderItemVoList =  Lists.newArrayList();
        List<MmallOrderItem> orderItemList = (List<MmallOrderItem>) response.getData();
        //获取订单总价
        BigDecimal payment = new BigDecimal("0");
        for (MmallOrderItem orderItem : orderItemList){
            payment = BigDecimalUtil.add(payment.doubleValue(), orderItem.getTotalPrice().doubleValue());
            //订单详情VO组装
            orderItemVoList.add(assembleOrderItemVo(orderItem));

        }
        //总价
        orderProductVo.setProductTotalPrice(payment);
        //明细
        orderProductVo.setOrderItemVoList(orderItemVoList);
        //图片服务器
        orderProductVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        return ServerResponse.createBySuccess(orderProductVo);
    }

    /**
     * 获取订单详情
     * @param userId
     * @param orderNo
     * @return
     */
    @Override
    public ServerResponse<OrderVo> detail(Integer userId, Long orderNo) {
        MmallOrder order = orderMapper.selectOrderByUserIdAndOrderNo(userId, orderNo);
        if (order == null){
            return ServerResponse.createByError("无该订单");
        }
        List<MmallOrderItem> orderItemList = orderItemMapper.getByOrderNoUserId(orderNo, userId);
        OrderVo orderVo= assembleOrderVo(order,orderItemList);
        return ServerResponse.createBySuccess(orderVo);

    }

    /**
     * 获取订单列表
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<MmallOrder> orderList = orderMapper.selectByUserId(userId);
        List<OrderVo> orderVoList = assembleOrderVoList(orderList,userId);
        PageInfo pageInfo = new PageInfo(orderList);
        pageInfo.setList(orderVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    /**
     * 管理员查看订单列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ServerResponse<PageInfo> manageList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<MmallOrder> orderList = orderMapper.selectAll();
        List<OrderVo> orderVoList = this.assembleOrderVoList(orderList,null);
        PageInfo pageInfo = new PageInfo(orderList);
        pageInfo.setList(orderVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    /**
     * 管理员查看订单详情
     * @param orderNo
     * @return
     */
    @Override
    public ServerResponse<OrderVo> manageDetail(Long orderNo) {
        MmallOrder order = orderMapper.selectByOrderNo( orderNo);
        if (order == null){
            return ServerResponse.createByError("无该订单");
        }
        List<MmallOrderItem> orderItemList = orderItemMapper.selectByOrderNo(orderNo);
        OrderVo orderVo= assembleOrderVo(order,orderItemList);
        return ServerResponse.createBySuccess(orderVo);
    }

    /**
     * 管理员分页搜索
     * @param orderNo
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ServerResponse<PageInfo> manageSearch(Long orderNo,int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        MmallOrder order = orderMapper.selectByOrderNo(orderNo);
        if (order == null){
            return ServerResponse.createByError("无该订单");
        }
        List<MmallOrderItem> orderItemList = orderItemMapper.selectByOrderNo(orderNo);
        OrderVo orderVo= assembleOrderVo(order,orderItemList);
        PageInfo pageInfo = new PageInfo(Lists.newArrayList(order));
        pageInfo.setList(Lists.newArrayList(orderVo));
        return ServerResponse.createBySuccess(pageInfo);
    }

    /**
     *管理员发货
     * @param orderNo
     * @return
     */
    @Override
    public ServerResponse<String> manageSendGoods(Long orderNo) {
        MmallOrder order = orderMapper.selectByOrderNo(orderNo);
        if (order != null){
            if (order.getStatus() == Const.OrderStatusEnum.PATD.getCode()){
                order.setStatus(Const.OrderStatusEnum.SHIPPED.getCode());
                order.setSendTime(new Date());
                orderMapper.updateByPrimaryKeySelective(order);
                return ServerResponse.createBySuccess("发货成功");
            }
        }
        return ServerResponse.createByError("无该订单");

    }

    /**
     * 组装返回给前端的订单VO列表
     * @param orderList
     * @return
     */
    private List<OrderVo> assembleOrderVoList(List<MmallOrder> orderList, Integer userId){
        List<OrderVo> orderVoList = Lists.newArrayList();
        for (MmallOrder order : orderList){

            List<MmallOrderItem> orderItemList = null;
            if (userId == null){
                //管理员登录不需要传userId,因为管理员可以看到任何一个账单
                orderItemList  =orderItemMapper.selectByOrderNo(order.getOrderNo());

            }else{
                orderItemList = orderItemMapper.getByOrderNoUserId(order.getOrderNo(),order.getUserId());
            }
            //组装OrderVO
            OrderVo orderVo = assembleOrderVo(order,orderItemList);

            //添加到列表
            orderVoList.add(orderVo);

        }
        return orderVoList;
    }
    /**
     *  组装返回给前端的订单VO
     * @param order
     * @param orderItemList
     */
    private OrderVo assembleOrderVo(MmallOrder order, List<MmallOrderItem> orderItemList){

        OrderVo orderVo = new OrderVo();
        //设置订单编号
        orderVo.setOrderNo(order.getOrderNo());
        //设置支付金额
        orderVo.setPayment(order.getPayment());
        //设置支付方式
        orderVo.setPaymentType(order.getPaymentType());
        //支付方式描述
        orderVo.setPaymentTypeDesc(Const.PaymentType.codeOf(order.getPaymentType()).getValue());
        orderVo.setPostage(order.getPostage());
        //设置支付状态
        orderVo.setStatus(order.getStatus());
        //设置支付状态描述
        orderVo.setStatusDesc(Const.OrderStatusEnum.codeOf(order.getStatus()).getValue());
        //设置收货地址
        System.out.println("OrdershippingId:"+order.getShippingId());
        MmallShipping shipping = shippingMapper.selectByPrimaryKey(order.getShippingId());
        if (shipping != null){
            orderVo.setReceiverName(shipping.getReceiverName());
            orderVo.setShippingId(order.getShippingId());
            orderVo.setShippingId(order.getShippingId());
            orderVo.setShippingVo(assembleShippingVo(shipping));
        }

        //设置支付时间
        orderVo.setPaymentTime(DateTimeUtil.dateToStr(order.getPaymentTime()));
        //发货时间
        orderVo.setSendTime(DateTimeUtil.dateToStr(order.getSendTime()));
        //结束时间
        orderVo.setCloseTime(DateTimeUtil.dateToStr(order.getCloseTime()));
        //新建时间
        orderVo.setCreateTime(DateTimeUtil.dateToStr(order.getCreateTime()));
        //设置图片服务器地址
        orderVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        //设置订单详情
        List<OrderItemVo> orderItemVoList = Lists.newArrayList();
        for (MmallOrderItem orderItem : orderItemList){
            orderItemVoList.add(assembleOrderItemVo(orderItem));
        }
        orderVo.setOrderItemVoList(orderItemVoList);
        return orderVo;
    }

    /**
     * 组装返回给前端的收获地址VO
     * @param shipping
     * @return
     */
    private ShippingVo assembleShippingVo(MmallShipping shipping){
        System.out.println("shipping:"+shipping.getId());
        ShippingVo shippingVo = new ShippingVo();
        shippingVo.setReceiverAddress(shipping.getReceiverAddress());
        shippingVo.setReceiverCity(shipping.getReceiverCity());
        shippingVo.setReceiverDistrict(shipping.getReceiverDistrict());
        shippingVo.setReceiverMobile(shipping.getReceiverMobile());
        shippingVo.setReceiverName(shipping.getReceiverName());
        shippingVo.setReceiverPhone(shipping.getReceiverPhone());
        shippingVo.setReceiverProvince(shipping.getReceiverProvince());
        shippingVo.setReceiverZip(shipping.getReceiverZip());
        return shippingVo;
    }

    /**
     * 组装返回给前端的订单明细VO
     * @param orderItem
     * @return
     */
    private OrderItemVo assembleOrderItemVo(MmallOrderItem orderItem){
        OrderItemVo orderItemVo = new OrderItemVo();
        orderItemVo.setCreateTime(DateTimeUtil.dateToStr(orderItem.getCreateTime()));
        orderItemVo.setCurrentUnitPrice(orderItem.getCurrentUnitPrice());
        orderItemVo.setOrderNo(orderItem.getOrderNo());
        orderItemVo.setProductId(orderItem.getProductId());
        orderItemVo.setProductImage(orderItem.getProductImage());
        orderItemVo.setProductName(orderItem.getProductName());
        orderItemVo.setQuantity(orderItem.getQuantity());
        orderItemVo.setTotalPrice(orderItem.getTotalPrice());
        orderItemVo.setUserId(orderItem.getUserId());
        return orderItemVo;
    }
    private void cleanCart(List<MmallCart> cartList ){
        for (MmallCart cartItem : cartList){
            cartMapper.deleteByPrimaryKey(cartItem.getId());
        }

    }

    /**
     * 封装订单成功后减少库存的方法
     * @param orderItemList
     */
    private void reduceProductStock(List<MmallOrderItem> orderItemList){
        for (MmallOrderItem orderItem : orderItemList){
            MmallProduct product = productMapper.selectByPrimaryKey(orderItem.getProductId());
            product.setStock(product.getStock()-orderItem.getQuantity());
            productMapper.updateByPrimaryKeySelective(product);
        }
    }
    /**
     * 组装订单方法
     * @param userId
     * @param shippingId
     * @param payment
     * @return
     */
    private MmallOrder assembleOrder(Integer userId, Integer shippingId, BigDecimal payment){
        MmallOrder order = new MmallOrder();
        //订单号生成
        Long orderNo = generateOrderNo();
        order.setOrderNo(orderNo);
        order.setPostage(0);
        order.setStatus(Const.OrderStatusEnum.NO_PAY.getCode());
        order.setPayment(payment);
        order.setPaymentType(Const.PaymentType.ONLINE_PAY.getCode());
        order.setUserId(userId);
        order.setShippingId(shippingId);
        order.setCreateTime(new Date());
        //发货时间等等
        //付款时间等等
        //在付款之中添加
        //判断添加是否成功
        int rowCount = orderMapper.insert(order);
        if (rowCount > 0){
            return order;
        }
        return null;





    }

    /**
     * 封装订单号生成规则
     * @return
     */
    private Long generateOrderNo(){
        //订单号生成的规则：当前时间+当前时间取余１０,也就是尾数０到９
        //同时并发会出错，所以最后改成随机数
        long currentTime = System.currentTimeMillis();
        return currentTime+new Random().nextInt(100);


    }

    /**
     * 封装订单明细总价格逻辑
     * @param orderItemList
     * @return
     */
    private BigDecimal getOrderTotalPrice(List<MmallOrderItem> orderItemList){
        BigDecimal payment = new BigDecimal("0");
        for (MmallOrderItem orderItem : orderItemList){
            payment = BigDecimalUtil.add(payment.doubleValue(),orderItem.getTotalPrice().doubleValue());

        }
        return payment;
    }
    /**
     * 封装订单明细部分逻辑
     * @param userId
     * @param cartList
     * @return
     */
    private ServerResponse<List<MmallOrderItem>> getCartOrderItem(Integer userId, List<MmallCart> cartList){
        //判断购物车是否为空
        List<MmallOrderItem> orderItemList = Lists.newArrayList();
        if (CollectionUtils.isEmpty(cartList)){
            return ServerResponse.createByError("购物车为空");
        }
        //检验购物车的数据，包括产品的数据和状态
        for (MmallCart cartItem : cartList){
            MmallOrderItem orderItem = new MmallOrderItem();
            //判断产品状态
            MmallProduct product = productMapper.selectByPrimaryKey(cartItem.getProductId());
            if (!(product.getStatus()== Const.ProductStatusEnum.ON_SALE.getCode())){
                return ServerResponse.createByError("产品"+product.getName()+"已经被删除或者下架");
            }
            //校验库存
            if (cartItem.getQuantity() > product.getStock()){
                return ServerResponse.createByError("库存不足");
            }
            //开始订单明细的组装

            orderItem.setUserId(userId);
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setProductImage(product.getMainImage());
            orderItem.setCurrentUnitPrice(product.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(BigDecimalUtil.mul(orderItem.getCurrentUnitPrice().doubleValue(), orderItem.getQuantity()));
            orderItemList.add(orderItem);

        }
        return ServerResponse.createBySuccess(orderItemList);
    }

    private static BaseClient.Config getOptions() {
        BaseClient.Config config = new BaseClient.Config();
        config.protocol = "https";
        config.gatewayHost = "openapi.alipaydev.com";
        config.signType = "RSA2";

        config.appId = "2016102200740135";

        // 为避免私钥随源码泄露，推荐从文件中读取私钥字符串而不是写入源码中
        config.merchantPrivateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCVsVGQtOQGNMdBI33MhlmgfC+OAq18QPWSVOcL6CuZP69f+Y5b1PpXyLI2LzjceqrEu8U6plkko9yr+iFH4JsLH9N56dyjNXN2sKQ9GyP6jWslbzTFzYka29t02OKSZECVl/s0WtwOeM1uj5iZiBBb2yE1RAIZxSWEClevw1CD7mxX1tv3JHmjxKd+vZNOnWX5KK1bd8BXWuwQ1tpGVQdOA2TNm4iINegDbYW82FUNft37Pl2YWzFJVuIz5kUTZu/2XuDiTMx2Vt2y9NLJof3iRghksrzXKVO/T/rVUC2Qbk9g6/1fC3jfvP6w1LGCxiP/lEO3V6CBdmxypuT95ZOjAgMBAAECggEAPQNBE5InPsBUVqB4rdw0u8ziNSeyPmrbrQEeCyReYOAsQxochCGSyCTtPliQ/Iyi/fzqrrUOsHh5gT86QWmyyB34vEAtxfgLT90/lAhz6bOqcF+bSuQzddcW49Fix/hEurRvly9RcjSaxu2QbUOgJ1BxgJxvnsFUQTVV+DXLo3nyyk7RhfwqjW/7ff1fM3vdBhkZLrtsmCLpC2P2dRjPzQQEtWYiwBVmGjxeGFJnglA2NMpFfQbteIcHGI4My/1MtqOMg1etrxK2aRmjMKsKkUWsmCmkO5WqVFsCQ6Uq38Stt+Y7WC6kCCSzWyP+tFXW5ypzPQoiLEZ7lnF2sTVRwQKBgQDLtNCZ1Dmx94wDoDLOqzxF+QW88zgS4TP/ZIN4z7QLgLBKuZ7W0DkvOvixn34y2i6+cH8+kv5ah6Mwd2TyJwj2LDdTKXJYL8ols4NmeFoU2sdnzUpWVYR9zVM/jL42mTkHoAHKE3odI3X4PW3ZfSlHANjJC3bE+YnCiZSjFcQorwKBgQC8HtSdyYrcb1I9JsGwxcZS95FGDIH3B8Cix6YW7inyAlfqYD8DFRAPWLmZHFnAemNwYWrDQQvGbmVDy8ove31ADtkXH+8RiXp+16aq25wjo+X/mxrYYzAWe1P+sGLnWrrJSAushm5bSX715EQEo/f+EYDhaeQV0K19PdHOQ2fZTQKBgGk1YeeRLJs19yKIQrVqyx54lvYXyAY4OdrV1vE2lQ670FFHvTnFIUO7eWaDsXIiLbI99/ohgSzQkQAfKgvHDWHXRFu3NE9Hn2gJZvwtfJj4xbRe2t3sHRm8ShqwXi1kxYeipEnx9UfHYM5tK6cAdakuTsjHGvhwyS2oURRJ0Z4RAoGASm2EP11C5E3i4rDXxyGogqDSSD2gmeTmUUDeNIpL7CUDNYaYx5ZowTmgTLSLyfxiJU9VlyTADOUWiYkyWDDqcBKvz1diFbUn0tFtboHut6cb01JBf8VWfD6VD9ond1NNPV+UxtB1Dwb4BT8nrhJVhp7UN9rWU885JKj1vj6q8q0CgYBqF0amA1uify47HfE4+N+kY2Sz1sarudQWsUfMnOIHK2QiVpMEBEC8Ys8wKCtx4ZLNYOKlcdc+S+LbKXT5NizwOWp4o7Z/LqGmQo7Z+MPnFNnSNaEX8o2ohxmsM2x8RLdh2NkgFF5oHoQ4j6Sk73MkfiuQUlZ+Dje5XkmJFU4KlA==";


        //注：如果采用非证书模式，则无需赋值上面的三个证书路径，改为赋值如下的支付宝公钥字符串即可
        config.alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmIOZT9rGLvtBMx1HLa9d14CIr44r51LCVUa5Ioxw30nlm5ElHuodacOSeskGm0C/TT4qgQHqOICHMBadMe6ceihbbDjEAKor919KQgWUlae1NAsRPqMlV2hinAUd+cy7Xp/pZ7BSeLz5BTFuiKfoG7XxOOy3ourLjvDQERJI0/FFKn6IcOrf1SSb5ypJTWjvOZ9cvTJzJ1gcATTcmxjYN8Hcwr8hl4LTA6+PLsVWtZXUjgrjKaE1ekxW1mbVMGeYKCxvaMowAoW1T8rAvyhtbEL0R0QFRWzwwphU3d/NKG+osiHWPkLVEXMjHvNSL+PO54L/NFYyWBkHPXGxOljKZQIDAQAB";

        //可设置异步通知接收服务地址（可选）
        config.notifyUrl = "http://lidengyin.nat300.top/order/alipay_callback.do";

        //可设置AES密钥，调用AES加解密相关接口时需要（可选）
        //config.encryptKey = "<-- 请填写您的AES密钥，例如：aa4BtZ4tspm2wnXLb1ThQA== -->";

        return config;
    }


}
