package com.nott.pms.sal.controller;


import com.nott.pms.common.Result;
import com.nott.pms.common.service.SessionService;
import com.nott.pms.sal.service.IEmpSalService;
import com.nott.pms.user.entity.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * 员工薪资表 前端控制器
 * </p>
 *
 * @author zzzwlong
 * @since 2022-03-31
 */
@RestController
@RequestMapping("/pms/sal")
public class EmpSalController {

    @Autowired
    private IEmpSalService empSalService;

    @Autowired
    private SessionService sessionService;

    @PostMapping("/querySalByEmpNo")
    @ApiOperation("员工查看薪资")
    public Result querySalByEmpNo(){
        User user = sessionService.getUser();
        return Result.success(empSalService.querySalByNo(user.getEmpNo()));
    }

    @RequestMapping(value = "/excel",produces = {"application/vnd.ms-excel;charset=UTF-8"})
    @ApiOperation("/导出excel表格")
    public void excel(HttpServletResponse response) throws IOException {
        User user = sessionService.getUser();
        empSalService.exportExcel(user.getEmpNo(),response);
       
    }
}

