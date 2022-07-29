//package com.nott.pms.config;
//
//import cn.hutool.core.lang.Snowflake;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @Author zzzwlong
// * @Date 2022/1/9
// */
//@Configuration
//public class SnowflakeConfig {
//
//    /**
//     * 雪花算法订单号生成
//     */
//    @Value("${application.datacenterId}")
//    private Long datacenterId;
//
//    @Value("${application.workerId}")
//    private Long workerId;
//
//    @Bean
//    public Snowflake snowflake() {
//        return new Snowflake(workerId, datacenterId);
//    }
//
//
//
//}
