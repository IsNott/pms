//package com.nott.pms.security.filter;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.nott.pms.common.Result;
//import com.nott.pms.security.TokenManager;
//import com.nott.pms.security.entity.SecurityUser;
//import com.nott.pms.utils.ResponseUtil;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//
///**
// * @Author Zouwenlong
// * Date on 2022/2/3 17:19
// */
//public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {
//    private AuthenticationManager authenticationManager;
//    private TokenManager tokenManager;
//    private RedisTemplate redisTemplate;
//
//    public TokenLoginFilter(AuthenticationManager authenticationManager, TokenManager tokenManager, RedisTemplate redisTemplate) {
//        this.authenticationManager = authenticationManager;
//        this.tokenManager = tokenManager;
//        this.redisTemplate = redisTemplate;
//        this.setPostOnly(false);
//        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/admin/acl/login", "POST"));
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
//            throws AuthenticationException {
//        try {
//            User user = new ObjectMapper().readValue(req.getInputStream(), User.class);
//
//            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), new ArrayList<>()));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
//
//    /**
//     * 登录成功
//     *
//     * @param req
//     * @param res
//     * @param chain
//     * @param auth
//     * @throws IOException
//     * @throws ServletException
//     */
//    @Override
//    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
//                                            Authentication auth) throws IOException, ServletException {
//        SecurityUser user = (SecurityUser) auth.getPrincipal();
//        String token = tokenManager.createToken(user.getUserInfo().getUsername());
//        redisTemplate.opsForValue().set(user.getUserInfo().getUsername(), user.getPermissionValueList());
//        HashMap<String, String> map = new HashMap<>();
//        map.put("token", token);
//        ResponseUtil.out(res, Result.success(map));
//    }
//
//    /**
//     * 登录失败
//     *
//     * @param request
//     * @param response
//     * @param e
//     * @throws IOException
//     * @throws ServletException
//     */
//    @Override
//    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
//                                              AuthenticationException e) throws IOException, ServletException {
//        ResponseUtil.out(response, Result.failure("登陆失败"));
//    }
//}
