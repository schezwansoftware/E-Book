package com.codesetters.ebook.service.impl;

import com.codesetters.ebook.security.AuthoritiesConstants;
import com.codesetters.ebook.service.MinioService;
import io.minio.MinioClient;
import io.minio.errors.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class MinioServiceImpl implements MinioService{
private static final String MINIO_SERVER="http://localhost:9000";
    @Override
    public String createMinioObject(MultipartFile file,String fileName) {
        try {

            InputStream minioStream=file.getInputStream();
            MinioClient client=new MinioClient(MINIO_SERVER, AuthoritiesConstants.MINIO_ACCES_KEY,AuthoritiesConstants.MINIO_SECRET_KEY);
            client.putObject("ebook",fileName,minioStream,minioStream.available(),file.getContentType());
        } catch (InvalidEndpointException e) {
            System.out.println("End Point not found");
            return "error";
        } catch (InvalidPortException e) {
            System.out.println("Port  not found");
            return "error";
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return "error";
        } catch (InvalidKeyException e) {
            System.out.println("Key not found");
            return "error";
        } catch (NoSuchAlgorithmException | XmlPullParserException | InvalidBucketNameException | InvalidArgumentException | InsufficientDataException | ErrorResponseException | InternalException | IOException e) {
            e.printStackTrace();
            return "error";
        } catch (NoResponseException e) {
            System.out.println("Sorry! No response returned from server");
            return "error";
        }
        return "success";
    }

    @Override
    public void getMinioObject() {

    }
}
