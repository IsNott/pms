package com.nott.pms.oss;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.nott.pms.exception.BusinessException;
import com.nott.pms.oss.entity.OssManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;



public class OssUtil {
    public static final Logger logger = LoggerFactory.getLogger(OssUtil.class);




//    public void upload(String dir, String fileName, InputStream imageStream) {
//        if (imageStream == null) {
//            throw new BusinessException("文件上传oss出错");
//        }
//
//        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
//        String endpoint = "https://" + ossManager.getEndpoint();
//        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
//        String accessKeyId = ossManager.getAccessKeyId();
//        String accessKeySecret = ossManager.getAccessKeySecret();
//        // 填写Bucket名称，例如examplebucket。
//        String bucketName = ossManager.getBucketName();
//        // 填写Object完整路径，例如exampledir/exampleobject.txt。Object完整路径中不能包含Bucket名称。
//        String objectName = dir + "/" + fileName;
//        // 填写本地文件的完整路径，例如D:\\localpath\\examplefile.txt。
//        // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
//        //String path = filePath;
//
//        // 创建OSSClient实例。
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//        try {
//            InputStream inputStream = imageStream;
//            // 创建PutObject请求。
//            ossClient.putObject(bucketName, objectName, inputStream);
//        } catch (Exception e) {
//            logger.error("error msg:" + e.getMessage());
//        } finally {
//            if (ossClient != null) {
//                ossClient.shutdown();
//            }
//        }
//    }
}

