//package com.nott.pms.security.filter;
//
//import com.nott.pms.exception.BusinessException;
//import com.nott.pms.exception.TokenValidationException;
//import com.nott.pms.utils.JwtUtils;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.util.AntPathMatcher;
//import org.springframework.util.PathMatcher;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.Map;
//
//import static com.nott.pms.utils.JwtUtils.ROLE;
//
//
///**
// * Token校验过滤器，确保需要校验的request都进行安全校验
// *
// * @Author zzzwlong
// * @Date 2022/1/8
// */
//public class JwtFilter extends OncePerRequestFilter {
//
//    //路径匹配器
//    public static final PathMatcher matcher = new AntPathMatcher();
//
//    //定义哪些路径不需要过滤的
//    private static String[] unProtectUrlPattern = {"/pms/login"};
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws TokenValidationException, IOException, ServletException {
//        try {
//            if (!isUnProtectPath(request.getServletPath())) {
//                //校验request的令牌
//                Map<String, Object> claims = JwtUtils.validateTokenAndGetClaims(request);
//                String role = String.valueOf(claims.get(ROLE));
//                String userId = String.valueOf(claims.get("userId"));
//                //SecurityContextHolder保存应用程序中当前使用人的安全上下文,setAuthentication设置当前使用者的信息
//                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
//                        //UsernamePasswordAuthenticationToken设置使用者的token信息
//                        userId, null, Arrays.asList(() -> role)));
//            }
//        } catch (Exception e) {
//            logger.error("Exception ：" + e);
//        }
//        filterChain.doFilter(request, response); //将请求转发到下一个过滤器，如果没有下一个就是请求资源
//    }
//
//    //私有路径匹配方法
//    private boolean isUnProtectPath(String reqUrl) {
//
//
//        for (int i = 0; i < unProtectUrlPattern.length; i++) {
//            if ((unProtectUrlPattern[i].equals(reqUrl))) ; //判断请求中的路径是否是需要过滤的
//            return false;
//        }
//        return true;
//    }
//}
