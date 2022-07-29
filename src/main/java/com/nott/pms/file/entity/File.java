package com.nott.pms.file.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 文件表
 * </p>
 *
 * @author zzzwlong
 * @since 2022-04-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="File对象", description="文件表")
public class File implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "文件名")
    private String fileName;

    @ApiModelProperty(value = "上传人id")
    @TableField("upload_No")
    private Long uploadNo;

    @ApiModelProperty(value = "文件路径")
    private String fileUrl;

    @ApiModelProperty(value = "上传时间")
    private LocalDateTime uploadTime;


}
