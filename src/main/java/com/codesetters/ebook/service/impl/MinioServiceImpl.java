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
    public InputStream getMinioObject(String filename) {
        InputStream minioStream=null;
        try {
            MinioClient client=new MinioClient(MINIO_SERVER, AuthoritiesConstants.MINIO_ACCES_KEY,AuthoritiesConstants.MINIO_SECRET_KEY);
            minioStream =client.getObject("ebook",filename);
        } catch (InvalidEndpointException e) {
            e.printStackTrace();
        } catch (InvalidPortException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoResponseException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Success");
        return minioStream;
    }
}
