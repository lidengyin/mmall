package cn.hcnet2006.mmall.mmall.controller.backend;

import cn.hcnet2006.mmall.mmall.bean.MmallUser;
import cn.hcnet2006.mmall.mmall.common.Const;
import cn.hcnet2006.mmall.mmall.common.ResponseCode;
import cn.hcnet2006.mmall.mmall.common.ServerResponse;
import cn.hcnet2006.mmall.mmall.service.IOrderService;
import cn.hcnet2006.mmall.mmall.service.IUserService;
import cn.hcnet2006.mmall.mmall.vo.OrderVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
@Api(tags = "订单管理接口")
@Controller
@RequestMapping("/manage/order/")
public class OrderManageController {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IUserService userService;
    /**
     * 后台获取订单列表
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */

    @RequestMapping(value = "list.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> orderList(@ApiIgnore HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        //判断用户是否登录
        MmallUser user = (MmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }
        //判断用户是否是管理员
        if (userService.checkAdminRole(user).isSuccess()){
            return orderService.manageList(pageNum,pageSize);
        }else {
            return ServerResponse.createByError("权限不足，请重新登录");
        }
    }

    /**
     * 管理员获取订单详情
     * @param session
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "detail.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<OrderVo> orderDetail(@ApiIgnore HttpSession session, Long orderNo){
        //判断用户是否登录
        MmallUser user = (MmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }
        //判断用户是否是管理员
        if (userService.checkAdminRole(user).isSuccess()){
            return orderService.manageDetail(orderNo);
        }else {
            return ServerResponse.createByError("权限不足，请重新登录");
        }
    }

    /**
     * 搜索订单
     * @param session
     * @param orderNo
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "search.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> orderSearch(@ApiIgnore HttpSession session, Long orderNo,@RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        //判断用户是否登录
        MmallUser user = (MmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }
        //判断用户是否是管理员
        if (userService.checkAdminRole(user).isSuccess()){
            return orderService.manageSearch(orderNo, pageNum, pageSize);
        }else {
            return ServerResponse.createByError("权限不足，请重新登录");
        }
    }

    /**
     * 发货
     * @param session
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "send_goods.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> sendGoods(@ApiIgnore HttpSession session, Long orderNo){
        //判断用户是否登录
        MmallUser user = (MmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }
        //判断用户是否是管理员
        if (userService.checkAdminRole(user).isSuccess()){
            return orderService.manageSendGoods(orderNo);
        }else {
            return ServerResponse.createByError("权限不足，请重新登录");
        }
    }
}
