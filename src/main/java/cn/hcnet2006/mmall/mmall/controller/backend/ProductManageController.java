package cn.hcnet2006.mmall.mmall.controller.backend;

import cn.hcnet2006.mmall.mmall.bean.MmallProduct;
import cn.hcnet2006.mmall.mmall.bean.MmallUser;
import cn.hcnet2006.mmall.mmall.common.Const;
import cn.hcnet2006.mmall.mmall.common.ResponseCode;
import cn.hcnet2006.mmall.mmall.common.ServerResponse;
import cn.hcnet2006.mmall.mmall.service.IFileService;
import cn.hcnet2006.mmall.mmall.service.IProductService;
import cn.hcnet2006.mmall.mmall.service.IUserService;
import cn.hcnet2006.mmall.mmall.util.PropertiesUtil;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 商品后台管理模块
 */
@Api(tags = "商品后台管理模块")
@Controller
@RequestMapping("/manage/product/")
public class ProductManageController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IProductService productService;

    @Autowired
    private IFileService fileService;
    /**
     * 商品保存或跟新
     * @param session

     * @return
     */
    @ApiOperation(value = "新增或修改商品",notes = "注意是属性选择性修改")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name = "categoryId", value = "分类ID",required = false),
            @ApiImplicitParam(type = "query", name = "detail", value = "商品详情",required = false),
            @ApiImplicitParam(type = "query", name = "id", value = "商品编号，有就是修改，没有就是添加",required = false),
            @ApiImplicitParam(type = "query", name = "name", value = "商品名",required = false),
            @ApiImplicitParam(type = "query", name = "stock", value = "商品库存",required = false),
            @ApiImplicitParam(type = "query", name = "subImages", value = "子图字符串逗号分隔",required = false),
            @ApiImplicitParam(type = "query", name = "subtitle", value = "副标题",required = false),
            @ApiImplicitParam(type = "query", name = "price", value = "单价",required = false)
    })
    @RequestMapping(value = "save.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse productSave(@ApiIgnore HttpSession session, BigDecimal price, Integer categoryId, Integer id , String detail, String name, Integer stock, String subImages, String subtitle){
        MmallProduct product = new MmallProduct();
        product.setStock(stock);
        product.setCategoryId(categoryId);
        product.setDetail(detail);
        product.setId(id);
        product.setName(name);
        product.setStock(stock);
        product.setSubImages(subImages);
        product.setSubtitle(subtitle);
        product.setPrice(price);
        //判断用户是否登录
        MmallUser user = (MmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }
        //判断用户是否是管理员
        if (userService.checkAdminRole(user).isSuccess()){
                return productService.saveOrUpdateProduct(product);
        }else {
            return ServerResponse.createByError("权限不足，请重新登录");
        }
    }


    /**
     * 更新产品上下架状态
     * @param session
     * @param productId
     * @param status
     * @return
     */
    @RequestMapping(value = "set_sale_status.do",method = RequestMethod.PUT)
    @ResponseBody
    public ServerResponse setSaleStatus(@ApiIgnore HttpSession session, Integer productId, Integer status){
        //判断用户是否登录
        MmallUser user = (MmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }
        //判断用户是否是管理员
        if (userService.checkAdminRole(user).isSuccess()){
            return productService.setSaleStatus(productId, status);
        }else {
            return ServerResponse.createByError("权限不足，请重新登录");
        }
    }

    /**
     * 获取商品详细信息
     * @param session
     * @param productId
     * @return
     */
    @RequestMapping(value = "detail.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse setSaleStatus(@ApiIgnore HttpSession session, Integer productId){
        //判断用户是否登录
        MmallUser user = (MmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }
        //判断用户是否是管理员
        if (userService.checkAdminRole(user).isSuccess()){
            return productService.manageProductDetail(productId);
        }else {
            return ServerResponse.createByError("权限不足，请重新登录");
        }
    }

    /**
     * 后台查询商品列表
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "list.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getList(@ApiIgnore HttpSession session,@RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10")int pageSize){
        //判断用户是否登录
        MmallUser user = (MmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }
        //判断用户是否是管理员
        if (userService.checkAdminRole(user).isSuccess()){
            return productService.getProductList(pageNum,pageSize);
        }else {
            return ServerResponse.createByError("权限不足，请重新登录");
        }
    }

    /**
     * 商品后台搜索功能
     * @param session
     * @param productName
     * @param productId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "search.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse productSearch(@ApiIgnore HttpSession session,String productName, Integer productId, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10")int pageSize){
        //判断用户是否登录
        MmallUser user = (MmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }
        //判断用户是否是管理员
        if (userService.checkAdminRole(user).isSuccess()){
            return productService.searchProduct(productName, productId,pageNum,pageSize);
        }else {
            return ServerResponse.createByError("权限不足，请重新登录");
        }
    }

    /**
     * 文件上传
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(value = "upload.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse upload(@ApiIgnore HttpSession session,@RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request){


        //判断用户是否登录
        MmallUser user = (MmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }
        //判断用户是否是管理员
        if (userService.checkAdminRole(user).isSuccess()){
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = fileService.upload(file,path);
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;
            Map fileMap = Maps.newHashMap();
            fileMap.put("uri",targetFileName);
            fileMap.put("url", url);
            return ServerResponse.createBySuccess(fileMap);
        }else {
            return ServerResponse.createByError("权限不足，请重新登录");
        }
    }
    @RequestMapping(value = "richtext_img_upload.do",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> irchTextImgupload(@ApiIgnore HttpSession session,@RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request){

        Map<String, Object> params = Maps.newHashMap();
        //判断用户是否登录
        MmallUser user = (MmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            if (user == null){
                params.put("state", "ERROR");
            }

                return params;
        }
        //判断用户是否是管理员
        if (userService.checkAdminRole(user).isSuccess()){
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = fileService.upload(file,path);
            String url = PropertiesUtil.getProperty("alipay.callback.url")+targetFileName;
            //富文本中对于返回值有自己的要求，我们使用的是ueditor,按照他的要求进行返回

            params.put("state", "SUCCESS");
            params.put("url", url);
            params.put("size", file.getSize());
            params.put("original", url);
            params.put("type", file.getContentType());
            return params;
        }else {
            return params;
        }
    }
    @RequestMapping(value = "upload_page.do",method = RequestMethod.GET)
    public String uploadpage(){
        return "upload";
    }
}