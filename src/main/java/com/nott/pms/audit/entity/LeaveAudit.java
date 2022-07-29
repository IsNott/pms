package com.nott.pms.audit.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author zzzwlong
 * @since 2022-03-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="LeaveAudit对象", description="")
public class LeaveAudit implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "申请理由")
    @TableField(value = "reason")
    private String reason;

    @ApiModelProperty(value = "审核人id，对应员工上级编号")
    @TableField(value = "auditor")
    private Long auditor;

    @ApiModelProperty(value = "提交人员工id")
    @TableField(value = "emp_no")
    private Long empNo;

    @ApiModelProperty(value = "申请类型 0为请假 1为离职")
    @TableField(value = "type")
    private Integer type;

    @ApiModelProperty(value = "审核状态 1为通过 0为审核中 -1为不通过")
    @TableField(value = "status")
    private Integer status;

    @ApiModelProperty(value = "申请时长")
    @TableField(value = "audit_duration")
    private Double auditDuration;

    @ApiModelProperty(value = "时长选项")
    @TableField(value = "audit_option")
    private Double auditOption;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField(value = "stattime")
    private LocalDateTime stattime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField(value = "endtime")
    private LocalDateTime endtime;


}
