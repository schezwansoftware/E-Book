package com.codesetters.ebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.codesetters.ebook.service.MinioService;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

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

    @GetMapping(value = "/downloadfile")
    @Timed
    @ResponseBody
    public void download(@RequestParam (value="filename") String filename,final HttpServletResponse response) {
        InputStream minioStream=minioService.getMinioObject(filename);
        response.reset();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename='"+filename+"'");
        try {
            OutputStream os=response.getOutputStream();
            IOUtils.copyLarge(minioStream,os);
            os.flush();
            System.out.println("seccess");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("failed");
        }

    }

    @GetMapping(value = "/viewfile")
    @Timed
    @ResponseBody
    public void view(@RequestParam (value="filename") String filename,final HttpServletResponse response) {
        InputStream minioStream=minioService.getMinioObject(filename);
        response.reset();
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename='"+filename+"'");
        try {
            OutputStream os=response.getOutputStream();
            IOUtils.copyLarge(minioStream,os);
            os.flush();
            System.out.println("seccess");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("failed");
        }

    }
}
