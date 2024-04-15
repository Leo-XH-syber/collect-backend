package com.example.backendcollect.utils;

import com.example.backendcollect.vo.file.FileInfoVO;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

@Component
@ConfigurationProperties(prefix = "spring.qcloud")
public class FileHelper {
    private static String secretId;
    private static String secretKey;
    private static String region;
    private static String bucketName;
    private static String url;
    private static String prefix;

    @Value("${spring.qcloud.SecretId}")
    public void setSecretId(String secretId) {
        FileHelper.secretId = secretId;
    }

    @Value("${spring.qcloud.SecretKey}")
    public void setSecretKey(String secretKey) {
        FileHelper.secretKey = secretKey;
    }

    @Value("${spring.qcloud.area}")
    public void setRegion(String region) {
        FileHelper.region = region;
    }

    @Value("${spring.qcloud.bucketName}")
    public void setBucketName(String bucketName) {
        FileHelper.bucketName = bucketName;
    }

    @Value("${spring.qcloud.url}")
    public void setUrl(String url) {
        FileHelper.url = url;
    }

    @Value("${spring.qcloud.prefix}")
    public void setPrefix(String prefix) {
        FileHelper.prefix = prefix;
    }

    public FileInfoVO save(MultipartFile file) throws IOException {
        String oldFileName = file.getOriginalFilename();
        assert oldFileName != null;
        String eName = oldFileName.substring(oldFileName.lastIndexOf("."));
        UUID uuid = UUID.randomUUID();
        String newFileName = uuid + eName;
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DATE);

        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        COSClient cosclient = new COSClient(cred, clientConfig);
        File localFile;
        try {
            localFile = File.createTempFile("temp", null);
            file.transferTo(localFile);
            String key = "/" + prefix + "/" + year + "/" + month + "/" + day + "/" + newFileName;
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
            PutObjectResult putObjectResult = cosclient.putObject(putObjectRequest);
            return new FileInfoVO(url + putObjectRequest.getKey(), file.getContentType(), file.getSize());
        } finally {
            cosclient.shutdown();
        }
    }
}
