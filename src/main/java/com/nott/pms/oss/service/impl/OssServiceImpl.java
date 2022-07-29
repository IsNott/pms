package com.nott.pms.oss.service.impl;


import com.aliyun.oss.*;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.OSSObject;
import com.nott.pms.exception.BusinessException;
import com.nott.pms.file.entity.File;
import com.nott.pms.oss.entity.OssManager;
import com.nott.pms.oss.service.OssService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.nott.pms.consts.Consts.FILEPATH;

@Service
@Slf4j
public class OssServiceImpl implements OssService {

    @Autowired
    private OssManager ossManager;

    public static final String TEMPPATH = "F:\\temp";

    @Override
    public void upload(String dir, String fileName, InputStream
            stream) {
        if (stream == null) {
            throw new BusinessException("文件上传oss出错");
        }

        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = "https://" + ossManager.getEndpoint();
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ossManager.getAccessKeyId();
        String accessKeySecret = ossManager.getAccessKeySecret();
        // 填写Bucket名称，例如examplebucket。
        String bucketName = ossManager.getBucketName();
        // 填写Object完整路径，例如exampledir/exampleobject.txt。Object完整路径中不能包含Bucket名称。
        String objectName = dir + "/" + fileName;
        // 填写本地文件的完整路径，例如D:\\localpath\\examplefile.txt。
        // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
        //String path = filePath;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            InputStream inputStream = stream;
            // 创建PutObject请求。
            ossClient.putObject(bucketName, objectName, inputStream);
        } catch (Exception e) {
            log.error("error msg:" + e.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    @Override
    public void remove(String deletePath) {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = "https://" + ossManager.getEndpoint();
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ossManager.getAccessKeyId();
        String accessKeySecret = ossManager.getAccessKeySecret();
        // 填写Bucket名称，例如examplebucket。
        String bucketName = ossManager.getBucketName();

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            ossClient.deleteObject(ossManager.getBucketName(), deletePath);
        } catch (Exception e) {
            log.error("error msg:" + e.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    @Override
    public void download(HttpServletResponse rep, File file) throws IOException {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = "https://" + ossManager.getEndpoint();
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ossManager.getAccessKeyId();
        String accessKeySecret = ossManager.getAccessKeySecret();
        // 填写Bucket名称，例如examplebucket。
        String bucketName = ossManager.getBucketName();
        //设置响应头内容
        rep.reset(); // 重置response的设置
        rep.setCharacterEncoding("utf-8");
        rep.setContentType("application/zip;charset=UTF-8");
        String name = "下载文件";
        String fileName = new String(name.getBytes(), "UTF-8");
        rep.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        BufferedInputStream bis = null;
        InputStream inputStream = null;
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(rep.getOutputStream());
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            Date expire = new Date(System.currentTimeMillis() + 3600 * 10);
            GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, FILEPATH + "/" + file.getFileName(), HttpMethod.GET);
            request.setExpiration(expire);
            URL url = ossClient.generatePresignedUrl(request);
            OSSObject ossObject = ossClient.getObject(url, new HashMap<>()); //获取OSS中的object
            if (ossObject != null) {
                inputStream = ossObject.getObjectContent();
                String zip = file.getFileName();
                ZipEntry zipEntry = new ZipEntry(zip);
                //创建缓冲流
                bis = new BufferedInputStream(inputStream);
                zos.putNextEntry(zipEntry);
                byte[] buf = new byte[1024];
                int length = 0;
                int read;
                while ((read = bis.read(buf)) != -1) {
                    zos.write(buf, 0, read);  // 把读的内容写成相应文件 写入到压缩包内
                }
                ossObject.close();
            }
        } catch (Exception e) {
            log.error("文件下载出错：" + e.getMessage());
        } finally {
            //关闭流
            if (null != bis) {
                bis.close();
            }
            if (null != inputStream) {
                inputStream.close();
            }
            if (null != zos) {
                zos.close();
            }
            rep.getOutputStream().flush();
            rep.getOutputStream().close();
        }

    }
}
