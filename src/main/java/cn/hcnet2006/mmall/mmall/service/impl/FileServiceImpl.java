package cn.hcnet2006.mmall.mmall.service.impl;

import cn.hcnet2006.mmall.mmall.service.IFileService;
import cn.hcnet2006.mmall.mmall.util.FTPUtil;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class FileServiceImpl implements IFileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    /**
     * 文件上传到ftp服务器
     * @param file
     * @param path
     * @return
     */
    @Override
    public String upload(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        //扩展名
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
        //防止文件名重复
        String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;

        logger.info("开始上传文件，上传文件的文件名:{},上传的路径是:{}，新文件名是:{}",fileName,path,uploadFileName);
        File fileDir = new File(path);
        //判断目录是否存在，如果不存在级联创造
        if (!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        //新建文件
        File targetFile = new File(fileDir,uploadFileName);
        try {
            //文件上传
            file.transferTo(targetFile);
            //将target文件上传到FTP服务器
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            //上传完成后，删除upload下面的文件
            targetFile.delete();
        }catch (Exception e){
            logger.error("上传文件异常",e);
            return null;
        }
        return targetFile.getName();
    }

    /**
     * 文件生成
     * @param file
     * @return
     */
    @Override
    public String upload(File file) {
        String fileName = file.getName();


        logger.info("开始上传文件，上传文件的文件名:{}",fileName);


        try {

            //将target文件上传到FTP服务器
            FTPUtil.uploadFile(Lists.newArrayList(file));
            //上传完成后，删除upload下面的文件
            file.delete();
        }catch (Exception e){
            logger.error("上传文件异常",e);
            return null;
        }
        return file.getName();
    }
}
