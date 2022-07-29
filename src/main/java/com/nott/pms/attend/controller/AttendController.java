package com.nott.pms.attend.controller;


import com.nott.pms.attend.service.IAttendService;
import com.nott.pms.common.Result;
import com.nott.pms.common.service.SessionService;
import com.nott.pms.user.entity.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zzzwlong
 * @since 2022-04-11
 */
@RestController
@RequestMapping("/pms/attend")
public class AttendController {
    @Autowired
    private SessionService sessionService;
    @Autowired
    private IAttendService attendService;

    @ApiOperation("考勤列表")
    @PostMapping("/list")
    public Result attendList(String date,Long page,Long rows) {
        User user = sessionService.getUser();
        return Result.success(attendService.getEmpAttendList(user.getEmpNo(),date,page,rows));
    }

}
