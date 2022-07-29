//package com.nott.pms.security.filter;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.nott.pms.account.entity.Account;
//import com.nott.pms.common.Result;
//import com.nott.pms.redis.CacheStorage;
//import com.nott.pms.security.service.CurrentService;
//import com.nott.pms.utils.JwtUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpMethod;
//import org.springframework.util.ObjectUtils;
//import org.springframework.web.filter.GenericFilterBean;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.Objects;
//
//import static com.nott.pms.config.WebSecurityConfig.LOGIN_ENTRY_PATH;
//import static com.nott.pms.consts.Consts.TOKENTIME;
//
///**
// * @Author Zouwenlong
// * Date on 2022/1/28 9:04
// */
//public class RefreshJwtTokenFilter extends GenericFilterBean {
//    private static final Logger log = LoggerFactory.getLogger(RefreshJwtTokenFilter.class);
//    private static final String AUTHENTICATION_PREFIX = "Bearer ";
//    private static final String AUTHENTICATION_HEADER = "UserInfo";
//    public static String REFRESHTOKENPATH;
//    @Autowired
//    private CurrentService currentService;
//
//    private CacheStorage cacheStorage;
//
//    public RefreshJwtTokenFilter(String refreshTokenEntryPoint) {
//        this.REFRESHTOKENPATH = refreshTokenEntryPoint;
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest req = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        StringBuffer url = req.getRequestURL();
//        Result result = null;
//        Account user = null;
//        if (this.REFRESHTOKENPATH.equals(url) && HttpMethod.POST.name().equals(req.getMethod())) {
//            String header = req.getHeader(AUTHENTICATION_HEADER);
//            if (Objects.nonNull(header)) {
//                user = currentService.getCurrentUser();
//                if (ObjectUtils.isEmpty(user)) {
//                    log.error("无登陆信息");
//                    result = new Result(-999, "missing userInfo");
//                }
//                String role = (user.getType().equals("1") ? "admin" : "user");
//                String userid = user.getId().toString();
//                String value = cacheStorage.get(userid, role);
//                if (Objects.nonNull(value)) {
//                    //判断缓存中的token和现在是否一致
//                    if (header.equals(value)) {
//                        cacheStorage.refresh(userid);
//                        log.info("refresh Token in redis of:" + userid + " , value :" + value);
//                        result = Result.success("token refresh finish", header);
//                    } else {
//                        result = new Result(-999, "header not equals token in cache");
//                    }
//                }
//
//            }
//        } else if(LOGIN_ENTRY_PATH.equals(req.getRequestURI())){
//            result = Result.ok();
//        }else {
//            logger.error("refresh token error");
//            result = new Result(-999, "refresh token error");
//        }
//        responseJsonWriter(response, result);
//        filterChain.doFilter(servletRequest, servletResponse);
//    }
//
//    /**
//     * 将response转换成通用结果
//     *
//     * @param response
//     * @param result
//     * @throws IOException
//     */
//    private static void responseJsonWriter(HttpServletResponse response, Result result) throws IOException {
//        response.reset();
//        response.setStatus(200);
//        response.setCharacterEncoding("utf-8");
//        response.setContentType("application/json");
//        ObjectMapper objectMapper = new ObjectMapper();
//        String resBody = objectMapper.writeValueAsString(result);
//        PrintWriter printWriter = response.getWriter();
//        printWriter.print(resBody);
//        printWriter.flush();
//        printWriter.close();
//
//    }
//}
