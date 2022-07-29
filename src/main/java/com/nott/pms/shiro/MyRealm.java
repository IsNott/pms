package com.nott.pms.shiro;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nott.pms.menu.entity.Permission;
import com.nott.pms.menu.entity.RolePermission;
import com.nott.pms.menu.entity.UserRole;
import com.nott.pms.menu.service.IPermissionService;
import com.nott.pms.menu.service.IRolePermissionService;
import com.nott.pms.menu.service.IUserRoleService;
import com.nott.pms.user.entity.User;
import com.nott.pms.user.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private IUserService userService;
    @Autowired
    HttpSession session;
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private IRolePermissionService rolePermissionService;
    @Autowired
    private IPermissionService permissionService;

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("执行授权doGetAuthorizationInfo");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        //获取当前用户
        User user = (User) subject.getPrincipal();
        Long roleId = user.getRoleId();
        UserRole role = userRoleService.getById(roleId);
        log.info("当前角色：" + role.getRoleName());
        session.setAttribute("role", role.getRoleName());
        LambdaQueryWrapper<RolePermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RolePermission::getPermissionId, role.getId());
        //获取当前的用户对应角色所有的权限
        List<RolePermission> rolePermissionList = rolePermissionService.list();
        ArrayList<String> list = new ArrayList<>();
        for (RolePermission rolePermission : rolePermissionList) {
            //获取权限
            Permission permission = permissionService.getById(rolePermission.getPermissionId());
            list.add(permission.getPermissionValue());
        }
        info.addStringPermissions(list);
        return info;
    }

    /**
     * 验证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("执行验证doGetAuthenticationInfo");
        UsernamePasswordToken passwordToken = (UsernamePasswordToken) authenticationToken;
        String username = passwordToken.getUsername();
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        User user = userService.getOne(wrapper);
        if (Objects.isNull(user)) {
            return null;
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), "");
        return info;
    }
}
