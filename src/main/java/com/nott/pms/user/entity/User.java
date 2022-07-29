package com.nott.pms.user.entity;

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
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.groups.Default;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author zzzwlong
 * @since 2022-02-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "User对象", description = "用户表")
@ToString
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "用户签名")
    private String token;

    @ApiModelProperty(value = "用户邮箱")
    private String Email;

    @ApiModelProperty(value = "员工编号")
    private Long empNo;

    @TableLogic
    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    private Integer delflag;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    @ApiModelProperty(value = "头像url")
    private String url;

    @ApiModelProperty(value = "权限id")
    private Long roleId;

    @TableField(exist = false)
    @ApiModelProperty(value = "用户手机号")
    private String Tel;



}
