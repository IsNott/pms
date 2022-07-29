package com.nott.pms.menu.controller;


import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nott.pms.common.Result;
import com.nott.pms.consts.SysConstant;
import com.nott.pms.menu.entity.Permission;
import com.nott.pms.menu.entity.RolePermission;
import com.nott.pms.menu.service.IPermissionService;
import com.nott.pms.menu.service.IRolePermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色权限 前端控制器
 * </p>
 *
 * @author zzzwlong
 * @since 2022-02-07
 */
@RestController
@RequestMapping("/pms/role-permission")
public class RolePermissionController {

    @Autowired
    private IRolePermissionService rolePermissionService;
    @Autowired
    private IPermissionService permissionService;

    @ApiOperation("获取角色菜单")
    @PostMapping("/list")
    public Result rolePerMission(Long roleId) {
        Assert.notNull(roleId, "权限id不能为空");
        LambdaQueryWrapper<RolePermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RolePermission::getDelflag, SysConstant.delflag.NORMAL)
                .eq(RolePermission::getRoleId, roleId);
        List<RolePermission> permissionList = rolePermissionService.list(wrapper);
        ArrayList<Permission> list = null;
        if (permissionList.size() > 0) {
            list = new ArrayList<>();
            for (RolePermission rolePermission : permissionList) {
                String permissionId = rolePermission.getPermissionId();
                Permission permission = permissionService.getById(Long.parseLong(permissionId));
                list.add(permission);
            }
        }
        return Result.success(list);
    }
}
