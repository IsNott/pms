//package com.nott.pms.config;
//
//import com.nott.pms.redis.CacheStorage;
//import com.nott.pms.security.MyLogoutHandler;
//import com.nott.pms.security.filter.TokenAuthenticationFilter;
//import com.nott.pms.security.filter.TokenLoginFilter;
//import com.nott.pms.security.TokenManager;
//import com.nott.pms.security.UnauthorizedEntryPoint;
//import com.nott.pms.security.filter.JwtFilter;
//
//import com.nott.pms.security.filter.RefreshJwtTokenFilter;
//import com.nott.pms.security.service.CurrentService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.AutoConfigureAfter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
///**
// * JWT配置类，配置类
// *
// * @Author zzzwlong
// * @Date 2022/1/8
// */
//@Slf4j
//@Configuration
//@AutoConfigureAfter({CrossConfig.class})
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)//开启基于方法的安全认证机制，也就是在web层的controller启用注解机制的安全确认
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//    private RedisTemplate redisTemplate;
//    private TokenManager tokenManager;
//
//    /**
//     * 登陆入口
//     */
//    public static final String LOGIN_ENTRY_PATH = "/pms/login";
//
//
//    /**
//     * 密码加密
//     * @return
//     */
//
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/api/**",
//                "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**"
//        );
//    }
//
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.exceptionHandling()
//                .authenticationEntryPoint(new UnauthorizedEntryPoint())
//                .and().csrf().disable()
//                .authorizeRequests()
//                .anyRequest().authenticated()
//                .and().logout().logoutUrl("/admin/acl/index/logout")
//                .addLogoutHandler(new MyLogoutHandler(tokenManager,redisTemplate)).and()
//                .addFilter(new TokenLoginFilter(authenticationManager(), tokenManager, redisTemplate))
//                .addFilter(new TokenAuthenticationFilter(authenticationManager(), tokenManager, redisTemplate)).httpBasic();
//    }
//
////    /**
////     * 自定义的Jwt令牌校验过滤器
////     *
////     * @return
////     */
////    @Bean
////    public JwtFilter jwtFilter() {
////        return new JwtFilter();
////    }
////
////    /**
////     * Token刷新过滤器
////     *
////     * @return
////     */
////    @Bean
////    public RefreshJwtTokenFilter refreshJwtTokenFilter() {
////        return new RefreshJwtTokenFilter(REFRESH_TOKEN_ENTRY_POINT);
////    }
//
//
//    /**
//     * SpringSecurity默认防火墙只支持默认的方法，重新编写一个StrictHttpFirewall可以支持全部的http方法
//     *
//     * @return
//     */
////    @Bean
////    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
////        StrictHttpFirewall firewall = new StrictHttpFirewall();
////        firewall.setAllowUrlEncodedSlash(true);
////        return firewall;
////    }
//
//}
//
