package com.cqupt.project.shop.service.impl;

import com.cqupt.project.shop.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author weigs
 * @date 2017/12/2 0002
 */
@Service
public class FileServiceImpl implements FileService {

    private static final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String upload(MultipartFile file, String path) {
        String filename = file.getOriginalFilename();
        //获取扩展名
        String extensionName = filename.substring(filename.lastIndexOf(".") + 1);
        String uploadFileName = UUID.randomUUID().toString() + "." + extensionName;

        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }

        File uploadFile = new File(path, uploadFileName);
        try {
            file.transferTo(uploadFile);

            //todo 上传到ftp服务器

            //todo 删除临时文件

        } catch (IOException e) {
            log.error("文件上传异常", e);
            return null;
        }

        return uploadFile.getName();
    }
}
