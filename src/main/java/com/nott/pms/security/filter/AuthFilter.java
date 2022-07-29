package com.nott.pms.security.filter;

import com.nott.pms.common.service.SessionService;
import com.nott.pms.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.tools.FileObject;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import java.io.IOException;

/**
 * @Author zzzwlong
 * @Date 2022/3/20 11:48
 */
@WebFilter("/pms/*")
public class AuthFilter implements Filter {
    //todo 权限验证过滤

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }
}
