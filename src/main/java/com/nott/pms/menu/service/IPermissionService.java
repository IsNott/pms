package com.nott.pms.menu.service;

import com.nott.pms.menu.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nott.pms.menu.vo.PermissionVo;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author zzzwlong
 * @since 2022-02-07
 */
public interface IPermissionService extends IService<Permission> {

    List<Permission> getPermissionByRoleId(Long roleId);

    boolean findUserPerValue(Long roleId,String perValue);

    List<PermissionVo> getMenuList();

    void changeMenu(Long id, Integer type);
}
