//package com.nott.pms.security.service;
//
//
//import com.nott.pms.exception.BusinessException;
//import com.nott.pms.user.entity.User;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//import org.springframework.util.ObjectUtils;
//import org.springframework.util.StringUtils;
//
//import java.util.Collection;
//
///**
// * @Author zzzwlong
// * @Date 2022/1/16
// */
//@Service
//public class CurrentService {
//    /**
//     * 获取当前登录用户的的Id
//     *
//     * @return
//     */
//    public String getCurrentUserId() {
//        String userid = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (StringUtils.isEmpty(userid)) {
//            throw new BusinessException("获取用户信息失败");
//        }
//        return userid;
//
//    }
//
//    /**
//     * 获取当前登录用户的角色
//     *
//     * @return
//     */
//    public String getCurrentRole() {
//        String role = null;
//        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
//        for (GrantedAuthority authority : authorities) {
//            role = authority.getAuthority();
//        }
//        if (StringUtils.isEmpty(role)) {
//            throw new BusinessException("获取角色失败");
//        }
//        return role;
//
//    }
//
//    /**
//     * 获取当前登录账户信息
//     *
//     * @return
//     */
//    public User getCurrentUser() {
//        User currUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (ObjectUtils.isEmpty(currUser)) {
//            throw new BusinessException("获取登录信息失败");
//        }
//        return currUser;
//    }
//}
