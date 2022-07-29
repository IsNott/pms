package com.nott.pms.user.controller;


import com.google.gson.JsonObject;
import com.nott.pms.common.Result;
import com.nott.pms.common.service.SessionService;
import com.nott.pms.consts.SysConstant;
import com.nott.pms.emp.service.IEmpService;
import com.nott.pms.exception.UserNotFoundExeption;
import com.nott.pms.user.entity.PwdParam;
import com.nott.pms.user.entity.User;
import com.nott.pms.user.entity.UserLoginParam;
import com.nott.pms.user.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.nott.pms.consts.Consts.CURRENT_USER;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author zzzwlong
 * @since 2022-02-04
 */
@RestController
@RequestMapping("/pms/user")
@Slf4j
public class UserController {
    @Autowired
    HttpSession session;
    @Autowired
    private IUserService userService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private IEmpService empService;

    @ApiModelProperty("登录")
    @PostMapping("/login")
    public Result login(@Valid UserLoginParam user, HttpServletRequest request) {
        return Result.success(userService.login(user.getUsername(), user.getPassword(), user.getCode(), request));
    }

    @ApiModelProperty("登出")
    @PostMapping("/logout")
    public Result logout() {
        userService.logout();
        return Result.ok();
    }

    @ApiModelProperty("未授权页面")
    @PostMapping("/unAuth")
    public Result unAuth() {
        return Result.failure(-1000, "未授权");
    }

    @RequestMapping("/toLogin")
    public Result toLogin() {
        return Result.failure(-10001, "未登录");
    }

    @ApiModelProperty("根据id获取用户信息")
    @PostMapping("/getInfoById")
    public Result getInfoById(String id) {
        User user = userService.getById(Long.parseLong(id));
        return Result.success(user);
    }

    @ApiModelProperty("用户修改密码")
    @PostMapping("/changePwd")
    public Result changePwd(HttpServletRequest request, @Valid PwdParam pwdParam) {
        User user = sessionService.getUser();
        userService.changePwd(request, pwdParam, user);
        return Result.ok();
    }

    @ApiModelProperty("查看用户列表")
    @PostMapping("/queryUserList")
    public Result userPage(Long size, Long page, String keyword) throws UserNotFoundExeption {
        return Result.success(userService.Userlist(size, page, keyword));
    }

    @ApiModelProperty("移除用户")
    @PostMapping("/remove")
    public Result userPage(String[] ids) {
        List<String> list = Arrays.asList(ids);
        for (String s : list) {
            long userId = Long.parseLong(s);
            User user = userService.getById(userId);
            if (Objects.nonNull(user)) {
                empService.removeById(user.getEmpNo());
            }
        }
        return Result.success(userService.removeByIds(Arrays.asList(ids)));
    }

    @ApiModelProperty("上传用户头像")
    @PostMapping(value = "/upload")
    public Result uploadUserfase(String data) throws IOException {
        Assert.notNull(data, "参数不能为空");
        User user = sessionService.getUser();
        userService.UploadUserface(data, user);
        return Result.ok();
    }

    @ApiModelProperty("用户个人信息更新")
    @PostMapping("/user/userupdate")
    public Result userupdate(@Valid User update) {
        User user = (User) session.getAttribute(CURRENT_USER);
        Assert.notNull(user, "用户为空");
        update.setUpdateTime(new Date());
        userService.updateById(update);
        return Result.ok();
    }

    @ApiModelProperty("获取头像接口")
    @GetMapping(value = "/face", produces = "image/jpeg")
    public void face(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) session.getAttribute(CURRENT_USER);
        userService.findUserfaceById(user.getId(), request, response);
    }


    @ApiModelProperty("管理员列表")
    @PostMapping("/adminList")
    public Result adminList() throws UserNotFoundExeption {
        //sessionService.auth(SysConstant.SYS_PERMISSION);
        User user = sessionService.getUser();
        return Result.success(userService.findAdmins(user.getEmpNo()));
    }

    @ApiModelProperty("删除管理员")
    @PostMapping("/removeAdmin")
    public Result removeAdmin(Long id) throws UserNotFoundExeption {
        //sessionService.auth(SysConstant.SYS_PERMISSION);
        User sessionServiceUser = sessionService.getUser();
        try {
            userService.removeById(id);
        } catch (Exception e) {
            return Result.failure(e.getMessage());
        }
        return Result.ok();
    }

}
