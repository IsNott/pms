package com.nott.pms.audit.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nott.pms.attend.vo.LeaveAuditVo;
import com.nott.pms.audit.entity.LeaveAudit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nott.pms.common.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzzwlong
 * @since 2022-03-02
 */
public interface ILeaveAuditService extends IService<LeaveAudit> {

    Page<LeaveAuditVo> auditList(Long empNo, Long page, Long size, Integer type);
}
