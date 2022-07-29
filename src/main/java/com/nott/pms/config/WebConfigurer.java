package com.nott.pms.config;

import com.nott.pms.security.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器注册
 *
 * @Author zzzwlong
 * @Date 2022/3/20 13:50
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {
    public void addInterceptors(InterceptorRegistry registry) {
        // 需要拦截的路径
        String[] addPathPatterns = {
                "/pms/*"
        };
        //不需要拦截的路径
        String[] excludePathPatterns = {
                "/pms/user/login",
                "pms/menu",
                "/doc.html/**",
                "/swagger-resources/**",
                "/swagger-ui.html/**",
                "/v2/api-docs/**",
        };
        // addPathPatterns("/**") 表示拦截所有的请求，
        // excludePathPatterns("/login", "/register") 表示除了登陆与注册之外，因为登陆注册不需要登陆也可以访问
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns(addPathPatterns).excludePathPatterns(excludePathPatterns);
        WebMvcConfigurer.super.addInterceptors(registry);
    }
//
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("localhost:8083/pms/user/login");
//        //设置优先级  当请求地址有重复的时候  执行优先级最高的
//        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
//        WebMvcConfigurer.super.addViewControllers(registry);
//    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html", "/v2/api-docs/", "/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
    }


}
