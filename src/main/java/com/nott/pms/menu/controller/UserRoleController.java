package com.nott.pms.menu.controller;


import com.nott.pms.common.Result;
import com.nott.pms.common.service.SessionService;
import com.nott.pms.consts.SysConstant;
import com.nott.pms.exception.UserNotFoundExeption;
import com.nott.pms.menu.service.IUserRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zzzwlong
 * @since 2022-02-07
 */
@RestController
@RequestMapping("/pms/role")
public class UserRoleController {
    @Autowired
    private SessionService sessionService;
    @Autowired
    private IUserRoleService userRoleService;

    @ApiOperation("系统角色列表")
    @PostMapping("/list")
    public Result roleList() throws UserNotFoundExeption {
        return Result.success(userRoleService.list());
    }

}
