package cn.hcnet2006.mmall.mmall.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface IFileService  {
    /**
     * 文件上传
     * @param file
     * @param path
     * @return
     */
    public String upload(MultipartFile file, String path);

    public String upload(File file) ;
}
