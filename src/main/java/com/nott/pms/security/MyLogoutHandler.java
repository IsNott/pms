//package com.nott.pms.security;
//
//import com.nott.pms.common.Result;
//import com.nott.pms.utils.ResponseUtil;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * @Author Zouwenlong
// * Date on 2022/2/3 17:10
// */
//@Component
//public class MyLogoutHandler implements org.springframework.security.web.authentication.logout.LogoutHandler {
//
//    private TokenManager tokenManager;
//    private RedisTemplate redisTemplate;
//
//    public MyLogoutHandler(TokenManager tokenManager, RedisTemplate redisTemplate) {
//        this.tokenManager = tokenManager;
//        this.redisTemplate = redisTemplate;
//    }
//
//    @Override
//    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
//        String token = request.getHeader("token");
//        if (!StringUtils.isEmpty(token)) {
//            tokenManager.removeToken(token);
//            //清空当前用户缓存中的权限数据
//            String userName = tokenManager.getUserFromToken(token);
//            redisTemplate.delete(userName);
//        }
//        ResponseUtil.out(response, Result.ok());
//    }
//}
