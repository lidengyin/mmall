package cn.hcnet2006.mmall.mmall.service;

import cn.hcnet2006.mmall.mmall.bean.MmallUser;
import cn.hcnet2006.mmall.mmall.common.ServerResponse;
import com.sun.org.apache.xpath.internal.objects.XObject;
import org.apache.catalina.Server;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface IUserService extends CurdService<MmallUser> {
    @Override
    int save(MmallUser record);

    @Override
    int delete(MmallUser record);

    @Override
    int delete(List<MmallUser> records);

    @Override
    MmallUser findById(Long id);

    @Override
    int update(MmallUser record);

    @Override
    int update(List<MmallUser> records);

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    ServerResponse<MmallUser> login(String username, String password);

    /**
     * 注册
     * @param user
     * @return
     */
    ServerResponse<String> registry(MmallUser user);

    /**
     * 注册时检测用户名，邮箱是否已经注册
     * @param str
     * @param type
     * @return
     */
    ServerResponse<String> registryValid(String str, String type);

    /**
     * 忘记密码时，根据用户名获取找回密码问题
     * @param username
     * @return
     */
    ServerResponse<String> forgetGetQuestion(String username);

    /**
     * 回答找回回密码问题
     * @param username
     * @param question
     * @param answer
     * @return
     */
    ServerResponse<String> checkAnswer(String username, String question, String answer);

    /**
     * 忘记密码的重置密码开发
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @return
     */
    ServerResponse<String> forgetRestPassword(String username, String passwordNew, String forgetToken);

    /**
     * 登录状态密码修改
     * @param passwordOld
     * @param passwordNew
     * @param user
     * @return
     */
    public ServerResponse<String> restPassword(String passwordOld, String passwordNew, MmallUser user);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    public ServerResponse<MmallUser> updateInformation(MmallUser user);

    /**
     * 获取用户个人信息
     * @param userId
     * @return
     */
    ServerResponse<MmallUser> get_information(Integer userId);

    /**
     * 是否拥有管理员权限
     * @param user
     * @return
     */
    ServerResponse checkAdminRole(MmallUser user);
}
