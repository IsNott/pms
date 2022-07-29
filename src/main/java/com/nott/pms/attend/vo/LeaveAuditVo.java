package com.nott.pms.attend.vo;

import com.nott.pms.audit.entity.LeaveAudit;
import lombok.Data;

@Data
public class LeaveAuditVo extends LeaveAudit {
    private String auditorName;
}
