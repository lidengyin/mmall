package cn.hcnet2006.mmall.mmall.controller.portal;

import cn.hcnet2006.mmall.mmall.bean.MmallUser;
import cn.hcnet2006.mmall.mmall.common.Const;
import cn.hcnet2006.mmall.mmall.common.ResponseCode;
import cn.hcnet2006.mmall.mmall.common.ServerResponse;
import cn.hcnet2006.mmall.mmall.service.ICartService;
import cn.hcnet2006.mmall.mmall.vo.CartVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;

/**
 * 购物车控制类
 */
@Api(tags = "购物车管理接口")
@Controller
@RequestMapping("/cart/")
public class CartController {

    @Autowired
    private ICartService cartService;

    /**
     * 添加购物车模块
     * @param session
     * @param count
     * @param productId
     * @return
     */
    @RequestMapping(value = "add.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<CartVo> add(@ApiIgnore HttpSession session, Integer count, Integer productId){
        MmallUser user= (MmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            //需要强制登录
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.add(user.getId(),productId,count);
    }

    /**
     * 修改购物车记录
     * @param session
     * @param count
     * @param productId
     * @return
     */
    @RequestMapping(value = "update.do",method = RequestMethod.PUT)
    @ResponseBody
    public ServerResponse update(@ApiIgnore HttpSession session, Integer count, Integer productId){
        MmallUser user= (MmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            //需要强制登录
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.update(user.getId(),productId, count);
    }

    /**
     * 删除购物车记录
     * @param session
     * @param productIds
     * @return
     */
    @RequestMapping(value = "delete_product.do",method = RequestMethod.DELETE)
    @ResponseBody
    public ServerResponse deleteProduct(@ApiIgnore HttpSession session, String productIds){
        MmallUser user= (MmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            //需要强制登录
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.deleteProduct(user.getId(),productIds);
    }

    /**
     * 返回购物车列表
     * @param session
     * @return
     */
    @RequestMapping(value = "list.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<CartVo> list(@ApiIgnore HttpSession session){
        MmallUser user= (MmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            //需要强制登录
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.list(user.getId());
    }
    //全选，全反选
    //单独选，单独反选
    //三个参数，userId, productId, checked
    @RequestMapping(value = "select_all.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse selectAll(@ApiIgnore HttpSession session){
        MmallUser user= (MmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            //需要强制登录
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.selectOrUnSelect(user.getId(),null,Const.Cart.CHECKED);
    }
    @RequestMapping(value = "un_select_all.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse unSelectAll(@ApiIgnore HttpSession session){
        MmallUser user= (MmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            //需要强制登录
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.selectOrUnSelect(user.getId(),null,Const.Cart.UNCHECKED);
    }

    /**
     * 单反选
     * @param session
     * @param productId
     * @return
     */
    @RequestMapping(value = "un_select.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse unSelect(@ApiIgnore HttpSession session, Integer productId){
        MmallUser user= (MmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            //需要强制登录
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.selectOrUnSelect(user.getId(),productId,Const.Cart.UNCHECKED);
    }

    /**
     * 单选
     * @param session
     * @param productId
     * @return
     */
    @RequestMapping(value = "select.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse Select(@ApiIgnore HttpSession session, Integer productId){
        MmallUser user= (MmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            //需要强制登录
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.selectOrUnSelect(user.getId(),productId,Const.Cart.CHECKED);
    }

    /**
     * 查询当前用户购物车内的商品数量
     * @param session
     * @return
     */
    @RequestMapping(value = "get_cart_product_count.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<Integer> getCartProductCount(@ApiIgnore HttpSession session){
        MmallUser user= (MmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            //没有登录只需要返回０就可以
            return ServerResponse.createBySuccess(0);
        }
        return cartService.getCartProductCount(user.getId());
    }
}
