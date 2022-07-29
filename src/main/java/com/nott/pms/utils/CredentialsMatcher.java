package com.nott.pms.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nott.pms.user.entity.User;
import com.nott.pms.user.service.IUserService;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

/**
 * 自定义加盐加密校验器
 */
public class CredentialsMatcher extends SimpleCredentialsMatcher {
    @Autowired
    private IUserService userService;

    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();
        String tokenPassword = String.valueOf(usernamePasswordToken.getPassword());

//        Md5Hash salt = new Md5Hash(tokenPassword, username);
//        if (salt.equals(user.getPassword())) {
//            return true;
//        }
        //因为数据库数据未加载为加密字符，所以使用明文
        if (tokenPassword.equals(info.getCredentials())) {
            return true;
        }
        return false;
    }

}
