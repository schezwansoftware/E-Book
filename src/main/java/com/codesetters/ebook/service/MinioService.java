package com.codesetters.ebook.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface MinioService {

    public String createMinioObject(MultipartFile file,String fileName);
    public InputStream getMinioObject(String filename);
}
