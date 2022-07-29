package com.nott.pms.detail.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 员工详细信息表
 * </p>
 *
 * @author zzzwlong
 * @since 2022-03-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="EmpDetail对象", description="员工详细信息表")
public class EmpDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "员工编号")
    private Long empNo;

    @ApiModelProperty(value = "国籍")
    private String nation;

    @ApiModelProperty(value = "民族")
    private String ethnic;

    @ApiModelProperty(value = "政治面貌")
    private String political;

    @ApiModelProperty(value = "籍贯")
    private String hometown;

    @ApiModelProperty(value = "联系地址")
    private String address;

    @ApiModelProperty(value = "婚姻状况 0未婚 1已婚")
    private String marital;

    @ApiModelProperty(value = "合同类型 0劳务合同 1劳动合同")
    private Integer contract;

    @ApiModelProperty(value = "合同年限")
    private Integer contractYears;

    @ApiModelProperty(value = "毕业院校")
    private String school;

    @ApiModelProperty(value = "学历")
    private String education;

    @ApiModelProperty(value = "职称")
    private String title;

    @ApiModelProperty(value = "身份证号")
    private String idCard;


}
