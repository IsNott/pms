package com.nott.pms.user.vo;

import com.nott.pms.menu.entity.Permission;
import com.nott.pms.user.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class AdminVo extends User {
    private List<Permission> permisssionName;
}
