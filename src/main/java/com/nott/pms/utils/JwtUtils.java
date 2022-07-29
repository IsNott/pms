//package com.nott.pms.utils;
//
//import cn.hutool.db.Session;
//
//import com.nott.pms.exception.TokenValidationException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//
///**
// * Jwt工具类，写基本的token有效期等
// *
// * @Author zzzwlong
// * @Date 2022/1/8
// */
//public class JwtUtils {
//    public static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
//    public static final long EXPIRATION_TIME = 60 * 60 * 1000;// 令牌环有效期
//    public static final String SECRET = "PmsZzzwlong";//令牌环密钥
//    public static final String TOKEN_PREFIX = "Bearer";//令牌环头标识（令牌前缀）
//    public static final String HEADER_STRING = "UserInfo";//配置令牌环在http heads中的键值
//    public static final String ROLE = "type";//自定义字段-角色字段
//
//    //生成令牌
//    public static String generateToken(String userRole, String userId) {
//        HashMap<String, Object> map = new HashMap<>(); //每个token对应一个角色,Token中显示用户角色、id
//        map.put(ROLE, userRole);
//        map.put("userId", userId);
//        String jwt = Jwts.builder()
//                .setClaims(map)
//                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//                .signWith(SignatureAlgorithm.HS512, SECRET)
//                .compact();
//        return TOKEN_PREFIX + " " + jwt;
//    }
//
//    //生成令牌环，自定义令牌过期时间
//    public static String generateToken(String userRole, String userid, long exprationtime) {
//        HashMap<String, Object> map = new HashMap<>();
//        map.put(ROLE, userRole);
//        map.put("userid", userid);
//        String jwt = null;
//        try {
//            jwt = Jwts.builder()
//                    .setClaims(map)
//                    .setExpiration(new Date(System.currentTimeMillis() + exprationtime))
//                    .signWith(SignatureAlgorithm.HS512, SECRET)
//                    .compact();
//        } catch (Exception e) {
//            throw new RuntimeException("token已过期");
//        }
//        return TOKEN_PREFIX + " " + jwt;
//    }
//
//    //令牌校验
//    public static Map<String, Object> validateTokenAndGetClaims(HttpServletRequest request) {
//        String token = request.getHeader(HEADER_STRING);
//        if (token == null) {
//            //无token时抛自定义异常
//            logger.error(request.getRequestURL() + "：error validateToken");
//            throw new TokenValidationException("Missing Token");
//        } else {
//            Map<String, Object> body = Jwts.parser()
//                    .setSigningKey(SECRET)
//                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
//                    .getBody();
//            return body;
//        }
//    }
//
//
//}
