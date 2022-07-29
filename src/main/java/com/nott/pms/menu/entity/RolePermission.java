package com.nott.pms.menu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色权限
 * </p>
 *
 * @author zzzwlong
 * @since 2022-02-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="RolePermission对象", description="角色权限")
public class RolePermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("role_id")
    private Long roleId;

    @TableField("permission_id")
    private String permissionId;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    private Integer delflag;


}
