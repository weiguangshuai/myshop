package com.cqupt.project.shop.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author weigs
 * @date 2017/12/2 0002
 */
public interface FileService {
    /**
     * 上传文件
     *
     * @param file
     * @param path
     * @return
     */
    String upload(MultipartFile file, String path);
}
