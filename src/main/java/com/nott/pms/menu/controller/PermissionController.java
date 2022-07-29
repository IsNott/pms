package com.nott.pms.menu.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nott.pms.common.Result;
import com.nott.pms.common.service.SessionService;
import com.nott.pms.consts.SysConstant;
import com.nott.pms.exception.UserNotFoundExeption;
import com.nott.pms.menu.entity.RolePermission;
import com.nott.pms.menu.service.IPermissionService;
import com.nott.pms.menu.service.IRolePermissionService;
import com.nott.pms.menu.service.IUserRoleService;
import com.nott.pms.user.entity.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static com.nott.pms.consts.Consts.CURRENT_USER;

/**
 * <p>
 * 权限 前端控制器
 * </p>
 *
 * @author zzzwlong
 * @since 2022-02-07
 */
@RestController
@RequestMapping("/pms/")
public class PermissionController {

    @Autowired
    private IUserRoleService roleService;
    @Autowired
    private IPermissionService permissionService;
    @Autowired
    HttpSession session;
    @Autowired
    private IRolePermissionService rolePermissionService;
    @Autowired
    private SessionService sessionService;

    @ApiOperation("获取菜单")
    @PostMapping("menu")
    public Result menu() throws UserNotFoundExeption {
        User user = (User) session.getAttribute(CURRENT_USER);
        if (null == user) {
            throw new UserNotFoundExeption("无登录信息");
        }
        return Result.success(permissionService.getPermissionByRoleId(user.getRoleId()));
    }

    @ApiOperation("菜单列表")
    @PostMapping("menu/list")
    public Result meneList() throws UserNotFoundExeption {
        return Result.success(permissionService.getMenuList());
    }

    @ApiOperation("修改菜单可用")
    @PostMapping("menu/changeMenu")
    public Result changeMenu(Integer delfalg, Long id) {
        permissionService.changeMenu(id, delfalg);
        return Result.ok();
    }

    @ApiOperation("角色授权")
    @PostMapping("/menu/auth")
    public Result auth(Integer delFlag, Long id, Long roleId) {
        if (delFlag == 1) {
            LambdaQueryWrapper<RolePermission> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(RolePermission::getRoleId, roleId).eq(RolePermission::getPermissionId, id).eq(RolePermission::getDelflag,SysConstant.delflag.NORMAL);
            RolePermission permission = rolePermissionService.list(wrapper).get(0);
            if (null != permission && permission.getDelflag() != 1) {
                permission.setDelflag(delFlag);
                rolePermissionService.updateById(permission);
            }
        } else{
            RolePermission permission = new RolePermission();
            permission.setPermissionId(String.valueOf(id));
            permission.setRoleId(roleId);
            rolePermissionService.save(permission);
        }
        return Result.ok();
    }
}



