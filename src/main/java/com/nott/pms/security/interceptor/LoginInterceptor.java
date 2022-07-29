package com.nott.pms.security.interceptor;

import com.nott.pms.common.service.SessionService;
import com.nott.pms.exception.UserNotFoundExeption;
import com.nott.pms.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 登录拦截器
 *
 * @Author zzzwlong
 * @Date 2022/3/20 13:52
 */

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    HttpSession session;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException, UserNotFoundExeption {
        log.info(request.getRequestURI());
        log.info("LoginInterceptor准备执行");
        boolean flag = false;
        if (!request.getRequestURI().endsWith("login") && null == request.getSession().getAttribute("user") && !request.getRequestURI().contains("pms/menu")) {
            log.info("LoginInterceptor执行");
            throw new UserNotFoundExeption("未登录");
        } else {
            flag = true;
        }
        return flag;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
