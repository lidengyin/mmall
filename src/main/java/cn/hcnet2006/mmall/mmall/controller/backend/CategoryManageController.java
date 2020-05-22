package cn.hcnet2006.mmall.mmall.controller.backend;

import cn.hcnet2006.mmall.mmall.bean.MmallUser;
import cn.hcnet2006.mmall.mmall.common.Const;
import cn.hcnet2006.mmall.mmall.common.ResponseCode;
import cn.hcnet2006.mmall.mmall.common.ServerResponse;
import cn.hcnet2006.mmall.mmall.service.ICategoryService;
import cn.hcnet2006.mmall.mmall.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;

/**
 * 分类节点管理
 */
@Api(tags = "分类节点管理接口")
@Controller
@RequestMapping("manage/category")
public class CategoryManageController {
    @Autowired
    private IUserService userService;
    @Autowired
    private ICategoryService categoryService;
    /**
     * 品类添加管理
     * @param session
     * @param categoryName
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/add_category.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addCategory(@ApiIgnore HttpSession session, String categoryName, @RequestParam(value = "parentId", defaultValue = "0") int parentId){
        MmallUser user = (MmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }
        //判断当前用户是否是管理员
        ServerResponse response = userService.checkAdminRole(user);
        if (response.isSuccess()){
            //添加品类
            return categoryService.addCategory(categoryName,parentId);
        }else{
            return ServerResponse.createByError("无权限操作，需要管理员权限");
        }

    }

    /**
     * 修改品类名称
     * @param session
     * @param categoryId
     * @param categoryName
     * @return
     */
    @RequestMapping(value = "set_category_name.do", method = RequestMethod.PUT)
    @ResponseBody
    public ServerResponse setCategoryName(@ApiIgnore HttpSession session, Integer categoryId, String categoryName){
        //判断当前用户是否登录
        MmallUser user = (MmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }
        //判断当前用户是否是管理员
        ServerResponse response = userService.checkAdminRole(user);
        if (response.isSuccess()){
            return categoryService.updateCategoryName(categoryId,categoryName);
        }else{
            return ServerResponse.createByError("无权限操作，需要管理员权限");
        }
    }

    /**
     * 获取当前品类下一级
     * @param session
     * @param categoryId
     * @return
     */
    @ApiOperation(value = "获取当前品类下一级")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name = "categoryId", value = "当前节点ID",required = false)
    })
    @RequestMapping(value = "/get_category.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getChildParallelCategory(@ApiIgnore HttpSession session,@RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId){
        //判断当前用户是否登录
        MmallUser user = (MmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }

            //查询category子节点的信息，并且无递归
            return categoryService.getChildrenParallelCategory(categoryId);

    }

    /**
     * 获取当前品类ID以及嵌套查询下一级品类ID
     * @param session
     * @param categoryId
     * @return
     */
    @ApiOperation(value = "获取当前品类ID以及嵌套查询下一级品类ID")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name = "categoryId", value = "当前节点ID")
    })
    @RequestMapping(value = "/get_deep_category.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(@ApiIgnore HttpSession session, @RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){
        //判断当前用户是否登录
        MmallUser user = (MmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }
            return categoryService.selectCategoryChildrenById(categoryId);
    }

    /**
     * 修改品类的状态
     * @param session
     * @param categoryId
     * @param status
     * @return
     */
    public ServerResponse setCategoryStatus(HttpSession session, Integer categoryId, boolean status){

        return null;
    }
}
