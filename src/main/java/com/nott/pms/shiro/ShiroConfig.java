package com.nott.pms.shiro;

import com.nott.pms.utils.CredentialsMatcher;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Configuration
public class ShiroConfig {

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();

        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
        //开放匿名接口
        linkedHashMap.put("/pms/user/login", "anon");
        linkedHashMap.put("/pms/user/toLogin", "anon");
        linkedHashMap.put("/user/unAuth", "anon");
        linkedHashMap.put("/captcha", "anon");

        //部门模块权限
        linkedHashMap.put("/pms/department/moveEmp", "perms[dept]");
        linkedHashMap.put("/pms/department/deptInfo", "perms[dept]");
        linkedHashMap.put("/pms/department/addDept", "perms[dept]");

        //信息公告模块
        linkedHashMap.put("/pms/msg/sendAnnou", "perms[msg]");
        linkedHashMap.put("/pms/msg/queryMsgByAdmin", "perms[msg]");

        //文件模块
        linkedHashMap.put("/pms/file/remove", "perms[file]");

        //用户管理模块
        linkedHashMap.put("/pms/user/queryUserList", "perms[user]");

        //系统管理模块
        linkedHashMap.put("/pms/user/adminList", "perms[sys]");
        linkedHashMap.put("/pms/user/removeAdmin", "perms[sys]");
        linkedHashMap.put("/pms/menu/list", "perms[sys]");
        linkedHashMap.put("/pms/menu/changeMenu", "perms[sys]");
        linkedHashMap.put("/pms/role/list", "perms[sys]");
        linkedHashMap.put("/pms/role-permission/**","perms[sys]");
        linkedHashMap.put("/pms/menu/auth","perms[sys]");

        //员工管理模块
        linkedHashMap.put("/emp/queryEmp", "perms[emp]");
        linkedHashMap.put("/emp/remove", "perms[emp]");
        linkedHashMap.put("/emp/add", "perms[emp]");
        linkedHashMap.put("/emp/detail/updateById", "perms[emp]");
        linkedHashMap.put("/emp/detail/add", "perms[emp]");
        linkedHashMap.put("/emp/detail/addDetail", "perms[emp]");


        //设置需要验证的接口，所有接口都需要验证后
        linkedHashMap.put("/**", "authc");

        //设置登录、未授权url
        bean.setLoginUrl("/pms/user/toLogin");
        bean.setUnauthorizedUrl("/pms/user/unAuth");
        bean.setSecurityManager(securityManager);
        bean.setFilterChainDefinitionMap(linkedHashMap);
        log.info("拦截器注入成功");
        return bean;
    }

    //自定义加密校验器
    @Bean(name = "credentialsMatcher")
    public CredentialsMatcher credentialsMatcher() {
        return new CredentialsMatcher();
    }


    //DefaultWebSecurityManager
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("myRealm") MyRealm myRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myRealm);
        return securityManager;
    }

    //Realm
    @Bean
    public MyRealm myRealm(@Qualifier("credentialsMatcher") CredentialsMatcher credentialsMatcher) {
        MyRealm realm = new MyRealm();
        realm.setCredentialsMatcher(credentialsMatcher);
        return realm;
    }

    //注册shiro过滤器
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {

        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        DelegatingFilterProxy filterProxy = new DelegatingFilterProxy();
        filterProxy.setTargetFilterLifecycle(true);
        filterProxy.setTargetBeanName("shiroFilter");
        registrationBean.setFilter(filterProxy);
        log.info("注册shiroFilter...");
        return registrationBean;
    }

    //开启注解
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
