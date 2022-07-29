//package com.nott.pms.security;
//
//import com.baomidou.mybatisplus.extension.api.R;
//import com.nott.pms.common.Result;
//import com.nott.pms.utils.ResponseUtil;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * @Author Zouwenlong
// * Date on 2022/2/3 17:04
// */
//public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {
//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//        ResponseUtil.out(response, Result.failure(500,"非授权访问"));
//    }
//}
