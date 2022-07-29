package com.nott.pms.oss.entity;


import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@ToString
@Configuration
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class OssManager {

    @Value("${oss.endpoint}")
    private String endpoint;
    @Value("${oss.accessKeyId}")
    private String accessKeyId;
    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${oss.bucketName}")
    private String bucketName;

    @Bean
    public OSSClient createOssClient() {
        return (OSSClient)new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }
}
