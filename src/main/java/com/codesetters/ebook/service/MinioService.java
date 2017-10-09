package com.codesetters.ebook.service;

import org.springframework.web.multipart.MultipartFile;

public interface MinioService {

    public String createMinioObject(MultipartFile file,String fileName);
    public void getMinioObject();
}
