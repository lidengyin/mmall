package cn.hcnet2006.mmall.mmall.controller.portal;

import cn.hcnet2006.mmall.mmall.bean.MmallUser;
import cn.hcnet2006.mmall.mmall.common.Const;
import cn.hcnet2006.mmall.mmall.common.ResponseCode;
import cn.hcnet2006.mmall.mmall.common.ServerResponse;
import cn.hcnet2006.mmall.mmall.service.IOrderService;
import cn.hcnet2006.mmall.mmall.util.PropertiesUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Map;
@Api(tags = "前台订单管理接口")
@Controller
@RequestMapping("/order/")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private IOrderService iOrderService;

    /**
     * 创建订单接口
     * @param session
     * @param shippingId
     * @return
     */
    @ApiOperation(value = "提交订单",notes = "提交购物车中商品，生成订单")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name = "shippingId", value = "订单收货地址,必须加，且必须是存在的",required = true)
    })
    @RequestMapping(value = "create.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse create(@ApiIgnore HttpSession session, @RequestParam(name = "shippingId",required = true) Integer shippingId){
        MmallUser user = (MmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());

        }

        return iOrderService.createOrder(user.getId(),shippingId);
    }

    /**
     * 订单取消
     * @param session
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "cancel.do",method = RequestMethod.PUT)
    @ResponseBody
    public ServerResponse cancel(@ApiIgnore HttpSession session, Long orderNo){
        MmallUser user = (MmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());

        }

        return iOrderService.cancelOrder(user.getId(),orderNo);
    }

    /**
     * 购物车确定购买时的订单预览
     * @param session

     * @return
     */
    @RequestMapping(value = "get_order_cart_product.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getOrderCartProduct(@ApiIgnore HttpSession session){
        MmallUser user = (MmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());

        }

        return iOrderService.getOrderCartProduct(user.getId());
    }

    /**
     * 查看订单详情
     * @param session
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "detail.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse detail(@ApiIgnore HttpSession session, Long orderNo){
        MmallUser user = (MmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());

        }

        return iOrderService.detail(user.getId(),orderNo);
    }


    /**
     * 查看订单列表
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "list.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> list(@ApiIgnore HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        MmallUser user = (MmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());

        }

        return iOrderService.list(user.getId(),pageNum,pageSize);
    }
    /**
     * 订单支付
     * @param session
     * @param orderNo
     * @param request
     * @return
     */
    @RequestMapping(value = "pay.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse pay(@ApiIgnore HttpSession session, Long orderNo, HttpServletRequest request){
        MmallUser user = (MmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());

        }
        String path = request.getSession().getServletContext().getRealPath("upload");
        return iOrderService.pay(orderNo, user.getId(), path);
    }

    /**
     * 授权回调
     * @return
     */
    @RequestMapping(value = "alipay_callback.do",method = RequestMethod.POST)
    @ResponseBody
    public Object rollBack(@ApiIgnore HttpServletRequest request){
        Map<String, String> params = Maps.newHashMap();
        Map requestParams = request.getParameterMap();
        for (Iterator iter =requestParams.keySet().iterator(); iter.hasNext(); ){
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++){
                valueStr=((i == values.length - 1)? valueStr+values[i] : valueStr+values[i]+",");
            }
            params.put(name, valueStr);
        }
        logger.info("支付宝回调,sign:{}, trade_status:{},参数:{}",params.get("sign"),params.get("trade_status"),params.toString());
        //样正回调的正确性，是不是支付宝发送，并且验证回调的正确性
        //除去签名签名类型
        params.remove("sign_type");
        boolean alipayRSACheckedV2 = false;
        try {
            alipayRSACheckedV2 = AlipaySignature.rsaCheckV2(params, PropertiesUtil.getProperty("alipay_public_key"),"UTF-8","RSA2");
            if (!alipayRSACheckedV2){
                 return  ServerResponse.createByError("非法请求，已经报警");
            }

        } catch (AlipayApiException e) {
            logger.error("支付宝验证回调异常",e);
        }
        //todo 验证各种数据
        ServerResponse serverResponse = iOrderService.alipayCallback(params);
        if (serverResponse.isSuccess()){
            return Const.AlipayCallback.RESPONSE_SUCCESS;
        }
        return Const.AlipayCallback.RESPONSE_FAILED;
    }

    /**
     * 查询订单状态
     * @param session
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "query_order_pay_status.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Boolean> queryOrderPayStatus(@ApiIgnore HttpSession session, Long orderNo){

        MmallUser user = (MmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());

        }
        ServerResponse response = iOrderService.queryOrderPayStatys(user.getId(), orderNo);
        if(response.isSuccess()){
            return ServerResponse.createBySuccess(true);
        }

        return ServerResponse.createBySuccess(false);
    }
}
