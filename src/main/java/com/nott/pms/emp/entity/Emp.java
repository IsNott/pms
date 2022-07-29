package com.nott.pms.emp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author zzzwlong
 * @since 2022-02-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Emp对象", description="")
public class Emp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "部门id")
    private Long deptno;

    @ApiModelProperty(value = "上级编号")
    private Long managerNo;

    @ApiModelProperty(value = "员工姓名")
    private String name;

    @ApiModelProperty(value = "员工电话")
    private String tel;

    @ApiModelProperty(value = "出勤状态 =1时请假，默认为0正常出勤")
    private Integer statu;

    @ApiModelProperty(value = "入职时间")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date entryTime;

    @ApiModelProperty(value = "在职状态 =1时离职，默认为0在职")
    @TableLogic
    private Integer deflag;

    @ApiModelProperty(value = "离职时间，默认为null")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date delTime;

    @ApiModelProperty(value = "职位")
    private String position;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "出生日期")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthday;


}
