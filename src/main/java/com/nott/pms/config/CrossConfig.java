package com.nott.pms.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.MultipartConfigElement;

/**
 * 跨域配置
 *
 * @Author zzzwlong
 * @Date 2021/12/20 16:32
 */
@Configuration
public class CrossConfig {


    @Bean
    public CorsFilter corsFilter() {

        CorsConfiguration config = new CorsConfiguration();
        //允许所有域名进行跨域调用,这里写* cookie可能不能使用
        config.addAllowedOriginPattern("*");
        //允许跨越发送cookie
        config.setAllowCredentials(true);
        //放行全部原始头信息
        config.addAllowedHeader("*");
        //允许所有请求方法跨域调用
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    /**
     * 配置上传文件大小的配置
     *
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //  单个数据大小
//        DataSize dataSize=
        factory.setMaxFileSize(DataSize.ofMegabytes(102400));
        /// 总上传数据大小
        factory.setMaxRequestSize(DataSize.ofMegabytes(102400));
        return factory.createMultipartConfig();
    }

}
