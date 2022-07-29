package com.nott.pms.menu.vo;

import com.nott.pms.menu.entity.Permission;
import lombok.Data;

import java.util.List;

@Data
public class PermissionVo extends Permission {
    private List<Permission> childrens;
}
