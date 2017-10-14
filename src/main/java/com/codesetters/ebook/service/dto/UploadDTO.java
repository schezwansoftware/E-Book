package com.codesetters.ebook.service.dto;

import org.springframework.web.multipart.MultipartFile;

public class UploadDTO {
    private String fileName;
    private MultipartFile uploadedFile;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public MultipartFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(MultipartFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }
}
