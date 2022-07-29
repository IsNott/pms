package com.nott.pms.message.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * <p>
 *
 * </p>
 *
 * @author zzzwlong
 * @since 2022-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
@ApiModel(value = "Message对象", description = "")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "发送人")
    @TableField("senderId")
    private Long senderid;

    @TableField(exist = false)
    private String sender;

    @ApiModelProperty(value = "文本信息")
    private String text;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value = "发送信息")
    private LocalDateTime sendtime;

    @ApiModelProperty(value = "接收人")
    @TableField("receiverId")
    private String receiverid;
    @TableField(exist = false)
    private String receiver;

    @ApiModelProperty(value = "状态 0未读 1已读")
    private Integer statu;

    @ApiModelProperty(value = "消息类型")
    private Integer type;


}
