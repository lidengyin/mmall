package cn.hcnet2006.mmall.mmall.controller.backend;

import cn.hcnet2006.mmall.mmall.bean.MmallUser;
import cn.hcnet2006.mmall.mmall.common.Const;
import cn.hcnet2006.mmall.mmall.common.ServerResponse;
import cn.hcnet2006.mmall.mmall.service.IUserService;
import cn.hcnet2006.mmall.mmall.service.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
@Api(tags = "管理员后台管理接口")
@Controller
@RequestMapping("/manage/user")
public class UserManagerController {

    @Autowired
    private IUserService userService;

    /**
     * 管理员登录
     * @param username
     * @param password
     * @param session
     * @return
     */
    @ApiOperation(value = "管理员登录")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name = "username", value = "用户名", required = true),
            @ApiImplicitParam(type = "query", name = "password", value = "密码", required = true)
    })
    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
    private ServerResponse login(String username, String password, @ApiIgnore HttpSession session){
        //判断是否登录陈宫
        ServerResponse<MmallUser> response = userService.login(username,password);
        if (response.isSuccess()){
            MmallUser user = response.getData();
            //判断登录者权限
            if (user.getRole() == Const.Role.ROLE_ADMIN){
                session.setAttribute(Const.CURRENT_USER, user);
                return response;
            }else{
                return ServerResponse.createByError("角色权限不足，无法登录");
            }
        }
        return response;

    }
}
