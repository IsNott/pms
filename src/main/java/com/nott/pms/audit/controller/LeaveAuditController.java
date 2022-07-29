package com.nott.pms.audit.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nott.pms.audit.entity.LeaveAudit;
import com.nott.pms.audit.service.ILeaveAuditService;
import com.nott.pms.common.Result;
import com.nott.pms.common.service.SessionService;
import com.nott.pms.consts.SysConstant;
import com.nott.pms.user.entity.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zzzwlong
 * @since 2022-03-02
 */
@RestController
@RequestMapping("/pms/audit")
public class LeaveAuditController {

    @Autowired
    private ILeaveAuditService leaveAuditService;
    @Autowired
    private SessionService sessionService;

    @ApiOperation("人事列表")
    @PostMapping("/list")
    public Result auditList(Long page,Long size,Integer type){
        User user = sessionService.getUser();
        return Result.success(leaveAuditService.auditList(user.getEmpNo(),page,size,type));

    }

    @ApiOperation("我的待处理审核")
    @PostMapping("/myAudit")
    public Result myAudit(){
        User user = sessionService.getUser();
        LambdaQueryWrapper<LeaveAudit> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LeaveAudit::getAuditor,user.getEmpNo()).eq(LeaveAudit::getStatus, SysConstant.status.WAIT);
        List<LeaveAudit> auditList = leaveAuditService.list(wrapper);
        return Result.success(auditList);
    }

    //todo 待审核功能
}
