package com.nott.pms.sal.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 员工薪资表
 * </p>
 *
 * @author zzzwlong
 * @since 2022-03-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="EmpSal对象", description="员工薪资表")
public class EmpSal implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "基础工资")
    private Double salary;

    @ApiModelProperty(value = "员工编号，对应emp中的id")
    private String empno;

    @ApiModelProperty(value = "员工津贴")
    private Double subsidy;

    @ApiModelProperty(value = "加班费时薪")
    private Double otSal;

    @ApiModelProperty(value = "加班时长")
    private Double overTime;

    @ApiModelProperty(value = "税前收入")
    private Double total;

    @ApiModelProperty(value = "税后收入")
    private Double totalAftax;

    @ApiModelProperty(value = "税率")
    private Double rate;


}
