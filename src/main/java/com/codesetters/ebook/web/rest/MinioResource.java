package com.codesetters.ebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.codesetters.ebook.service.MinioService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class MinioResource {
private final MinioService minioService;

    public MinioResource(MinioService minioService) {
        this.minioService = minioService;
    }
    @PostMapping(value = "/fileuploads")
    @Timed
    public String saveUploads(@RequestParam(value = "filename")String filename,@RequestParam(value = "file")MultipartFile file) throws JSONException {
       String response=minioService.createMinioObject(file,filename);
        if (response.equals("error")){
            return new JSONObject().put("message","error").toString();
        }else
        return new JSONObject().put("message","success").toString();
    }
}
