package com.codesetters.ebook.service.impl;

import com.codesetters.ebook.security.AuthoritiesConstants;
import com.codesetters.ebook.service.MinioService;
import io.minio.MinioClient;
import io.minio.errors.*;
import org.springframework.stereotype.Service;
import org.xmlpull.v1.XmlPullParserException;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class MinioServiceImpl implements MinioService{
public static final String MINIO_SERVER="http://localhost:9000";
    @Override
    public void createMinioObject() {
        try {
            File minioObject=new File("/var/uploads/abc.pdf");
            InputStream minioStream=new FileInputStream(minioObject);
            MinioClient client=new MinioClient(MINIO_SERVER, AuthoritiesConstants.MINIO_ACCES_KEY,AuthoritiesConstants.MINIO_SECRET_KEY);
            client.putObject("ebook","myfirstobject",minioStream,minioStream.available(),"application/pdf");

        } catch (InvalidEndpointException e) {
            System.out.println("End Point not found");
        } catch (InvalidPortException e) {
            System.out.println("Port  not found");
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (InvalidKeyException e) {
            System.out.println("Key not found");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoResponseException e) {
            System.out.println("Sorry! No response returned from server");
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
    }

    @Override
    public void getMinioObject() {

    }
}
