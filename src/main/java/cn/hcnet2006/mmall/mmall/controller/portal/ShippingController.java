package cn.hcnet2006.mmall.mmall.controller.portal;

import cn.hcnet2006.mmall.mmall.bean.MmallShipping;
import cn.hcnet2006.mmall.mmall.bean.MmallUser;
import cn.hcnet2006.mmall.mmall.common.Const;
import cn.hcnet2006.mmall.mmall.common.ResponseCode;
import cn.hcnet2006.mmall.mmall.common.ServerResponse;
import cn.hcnet2006.mmall.mmall.service.IShippingService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;

/**
 * 收货地址模块
 */
@Api(tags = "收货地址接口")
@Controller
@RequestMapping("/shipping/")
public class ShippingController {

    @Autowired
    private IShippingService shippingService;

    /**
     * 增加收货地址
     * @param session
     * @param shipping
     * @return
     */
    @RequestMapping(value = "add.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse add(@ApiIgnore HttpSession session , MmallShipping shipping){
        //判断当前登录用户是否为空
        MmallUser user = (MmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return shippingService.add(user.getId(), shipping);
    }

    /**
     * 删除收货地址
     * @param session
     * @param shippingId
     * @return
     */
    @RequestMapping(value = "del.do",method = RequestMethod.DELETE)
    @ResponseBody
    public ServerResponse delete(@ApiIgnore HttpSession session , Integer shippingId){
        //判断当前登录用户是否为空
        MmallUser user = (MmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByError("用户未登录");
        }
        return shippingService.delete(user.getId(), shippingId);
    }

    /**
     * 更新收货地址
     * @param session
     * @param shipping
     * @return
     */
    @RequestMapping(value = "update.do", method = RequestMethod.PUT)
    @ResponseBody
    public ServerResponse update(@ApiIgnore HttpSession session , MmallShipping shipping){
        //判断当前登录用户是否为空
        MmallUser user = (MmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return shippingService.update(user.getId(), shipping);
    }

    /**
     * 获取收货地址
     * @param session
     * @param shippingId
     * @return
     */
    @RequestMapping(value = "select.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<MmallShipping> select(@ApiIgnore HttpSession session , Integer shippingId){
        //判断当前登录用户是否为空
        MmallUser user = (MmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByError("用户未登录");
        }
        return shippingService.select(user.getId(), shippingId);
    }

    /**
     * 获取分页列表
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "list.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse list(@ApiIgnore HttpSession session , @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){
        //判断当前登录用户是否为空
        MmallUser user = (MmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByError("用户未登录");
        }
        return shippingService.list(user.getId(),pageNum,pageSize);
    }
}
