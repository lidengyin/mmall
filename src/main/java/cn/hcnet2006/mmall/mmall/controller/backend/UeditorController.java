package cn.hcnet2006.mmall.mmall.controller.backend;


import cn.hcnet2006.mmall.mmall.service.IFileService;
import cn.hcnet2006.mmall.mmall.ueditor.PublicMsg;
import cn.hcnet2006.mmall.mmall.util.PropertiesUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Api(tags = "富文本独立上传接口")
@Controller
public class UeditorController {

    @Autowired
    private IFileService fileService;

    @GetMapping("/indexPage")
    private String showPage(){
        return "index";
    }
   @ApiOperation(value = "返回富文本整体内容",notes = "返回富文本整体内容")
    @RequestMapping("/content")
    @ResponseBody
   @CrossOrigin(origins = "*", allowCredentials = "true",allowedHeaders = "*",methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.PUT, RequestMethod.POST, RequestMethod.PATCH})
    public String findContent( String content) throws IOException {
        //System.out.println("content1:"+content1);
        String url = ResourceUtils.getURL("").getPath()+UUID.randomUUID().toString()+".html";
        File folder = new File(url);
        FileWriter fileWriter = new FileWriter(folder);
        fileWriter.write(content);
        fileWriter.flush();
        fileWriter.close();
        fileService.upload(folder);
        url = PropertiesUtil.getProperty("ftp.server.http.prefix")+folder.getName();
        folder.delete();
        System.out.println("content:"+url);
        return url;
    }
    @ApiOperation(value = "设置富文本本地配置",notes = "设置富文本本地配置")
    @RequestMapping(value="/ueditor")
    @ResponseBody
    @CrossOrigin(origins = "*", allowCredentials = "true",allowedHeaders = "*",methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.PUT, RequestMethod.POST, RequestMethod.PATCH})
    public String ueditor(HttpServletRequest request) {

        return PublicMsg.UEDITOR_CONFIG;
    }
    @ApiOperation(value = "富文本中单独用于图片上传的接口")
    @RequestMapping(value="/imgUpload")
    @ResponseBody
    @CrossOrigin(origins = "*", allowCredentials = "true",allowedHeaders = "*",methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.PUT, RequestMethod.POST, RequestMethod.PATCH})
    public Map<String, Object> images (MultipartFile upfile, HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> params = new HashMap<String, Object>();
        System.out.println("1111111111111");
        try{

            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = fileService.upload(upfile,path);
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;
            String fileName = upfile.getOriginalFilename();
            params.put("state", "SUCCESS");
            params.put("url", url);
            params.put("size", upfile.getSize());
            params.put("original", fileName);
            params.put("type", upfile.getContentType());
            System.out.println("imageUrl:"+url);
        } catch (Exception e){
            params.put("state", "ERROR");
        }
        return params;
    }
@ApiOperation(value = "富文本单独用于视频上传的接口")
    @RequestMapping(value="/videoUpload")
    @ResponseBody
@CrossOrigin(origins = "*", allowCredentials = "true",allowedHeaders = "*",methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.PUT, RequestMethod.POST, RequestMethod.PATCH})
    public Map<String, Object> videos (MultipartFile upfile, HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> params = new HashMap<String, Object>();
        System.out.println("1111111111111");
        try{
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = fileService.upload(upfile,path);
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;
            String fileName = upfile.getOriginalFilename();
            params.put("state", "SUCCESS");
            params.put("url", url);
            params.put("size", upfile.getSize());
            params.put("original", fileName);
            params.put("type", upfile.getContentType());

        } catch (Exception e){
            params.put("state", "ERROR");
        }
        return params;
    }
@ApiOperation(value = "富文本单独用于附件上传的方法")
    @RequestMapping(value="/fileUpload")
    @ResponseBody
@CrossOrigin(origins = "*", allowCredentials = "true",allowedHeaders = "*",methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.PUT, RequestMethod.POST, RequestMethod.PATCH})
    public Map<String, Object> files (MultipartFile upfile, HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> params = new HashMap<String, Object>();
        System.out.println("1111111111111");
        try{
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = fileService.upload(upfile,path);
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;
            String fileName = upfile.getOriginalFilename();
            params.put("state", "SUCCESS");
            params.put("url", url);
            params.put("size", upfile.getSize());
            params.put("original", fileName);
            params.put("type", upfile.getContentType());
        } catch (Exception e){
            params.put("state", "ERROR");
        }
        return params;
    }


    @ApiOperation(value = "富文本中单独用于图片在线管理的接口")
    @RequestMapping(value="/listimage")
    @ResponseBody
    @CrossOrigin(origins = "*", allowCredentials = "true",allowedHeaders = "*",methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.PUT, RequestMethod.POST, RequestMethod.PATCH})
    public Map<String, Object> listimage (){
        Map<String, Object> params = new HashMap<String, Object>();
        System.out.println("1111111111111");
        try{

            params.put("state", "SUCCESS");
            params.put("url", "http://121.36.145.230/626dceb4-398e-481b-80e5-4339c3708a6e.jpg");
            params.put("size", 1111);
            params.put("original", "ss");
            params.put("type", "image");
            //System.out.println("imageUrl:"+url);
        } catch (Exception e){
            params.put("state", "ERROR");
        }
        return params;
    }
}