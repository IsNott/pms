package com.nott.pms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;


@MapperScan(basePackages = {"com.nott.pms.*.mapper"})
@EnableCaching
@SpringBootApplication
public class PmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PmsApplication.class, args);
    }

    @Bean
    public TomcatServletWebServerFactory mbeddedServletContainerFactory() {
        TomcatServletWebServerFactory tomcatEmbeddedServletContainerFactory = new TomcatServletWebServerFactory ();

        tomcatEmbeddedServletContainerFactory.addConnectorCustomizers(connector ->{
            connector.setMaxParameterCount(Integer.MAX_VALUE);
        });

        return tomcatEmbeddedServletContainerFactory;
    }

}
