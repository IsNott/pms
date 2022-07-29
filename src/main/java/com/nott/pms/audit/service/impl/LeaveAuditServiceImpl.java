package com.nott.pms.audit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nott.pms.attend.vo.LeaveAuditVo;
import com.nott.pms.audit.entity.LeaveAudit;
import com.nott.pms.audit.mapper.LeaveAuditMapper;
import com.nott.pms.audit.service.ILeaveAuditService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nott.pms.common.Result;
import com.nott.pms.emp.service.IEmpService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zzzwlong
 * @since 2022-03-02
 */
@Service
public class LeaveAuditServiceImpl extends ServiceImpl<LeaveAuditMapper, LeaveAudit> implements ILeaveAuditService {

    @Autowired
    private IEmpService empService;

    @Override
    public Page<LeaveAuditVo> auditList(Long empNo, Long page, Long size, Integer type) {
        Page<LeaveAuditVo> auditPage = new Page<>();
        Page<LeaveAudit> leaveAuditPage = new Page<>();
        List<LeaveAudit> list = null;
        if (Objects.nonNull(page) && page > 0) {
            leaveAuditPage.setCurrent(page);
        }
        if (Objects.nonNull(size) && size > 0) {
            leaveAuditPage.setSize(size);
        }
        LambdaQueryWrapper<LeaveAudit> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LeaveAudit::getEmpNo, empNo)
                .eq(Objects.nonNull(type), LeaveAudit::getType, type);
        list = this.list(wrapper);
        Page<LeaveAudit> auditPage1 = this.page(leaveAuditPage, wrapper);
        List<LeaveAudit> leaveAuditList = auditPage1.getRecords();
        ArrayList<LeaveAuditVo> vos = new ArrayList<>();
        for (LeaveAudit audit : leaveAuditList) {
            LeaveAuditVo vo = new LeaveAuditVo();
            BeanUtils.copyProperties(audit, vo);
            if(empService.getById(audit.getAuditor()) !=null) {
                vo.setAuditorName(empService.getById(audit.getAuditor()).getName());
            }else {
                vo.setAuditorName("system");
            }
            vos.add(vo);
        }
        auditPage.setRecords(vos);
        auditPage.setTotal(list.size());
        return auditPage;
    }
}
