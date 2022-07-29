package com.nott.pms.menu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nott.pms.consts.SysConstant;
import com.nott.pms.exception.BusinessException;
import com.nott.pms.menu.entity.Permission;
import com.nott.pms.menu.entity.RolePermission;
import com.nott.pms.menu.mapper.PermissionMapper;
import com.nott.pms.menu.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nott.pms.menu.service.IRolePermissionService;
import com.nott.pms.menu.vo.PermissionVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author zzzwlong
 * @since 2022-02-07
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Autowired
    private IRolePermissionService rolePermissionService;

    public static Logger logger = LoggerFactory.getLogger(PermissionServiceImpl.class);

    @Override
    public List<Permission> getPermissionByRoleId(Long roleId) {
        List<Permission> menu = baseMapper.selectPemission(roleId);
        for (Permission permission : menu) {
            List<Permission> children = baseMapper.selectChildrenMenu(permission.getId());
            permission.setChildren(children);
        }
        logger.info("user menu:" + menu);
        return menu;
    }

    @Override
    public boolean findUserPerValue(Long roleId, String perValue) {
        QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(RolePermission::getRoleId, roleId);
        List<RolePermission> rolePermissions = rolePermissionService.list(wrapper);

        List<Permission> permissions = new ArrayList<>();

        for (RolePermission rolePermission : rolePermissions) {
            QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(Objects.nonNull(rolePermission), Permission::getId, rolePermission.getPermissionId());
            Permission permission = this.list(queryWrapper).get(0);
            permissions.add(permission);
        }
//        logger.info("permission" + permissions);
        if (CollectionUtils.isEmpty(permissions)) {
            throw new BusinessException("无查看权限");
        }
        for (Permission permission : permissions) {
            if (!StringUtils.isEmpty(baseMapper.selectPerValueByPerIdandValue(permission.getId(), perValue))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<PermissionVo> getMenuList() {
        ArrayList<PermissionVo> vos = new ArrayList<>();
        LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Permission::getPid,"").or().isNull(Permission::getPid );
        List<Permission> pMenu = this.list(wrapper);
        for (Permission menu : pMenu) {
            PermissionVo vo = new PermissionVo();
            BeanUtils.copyProperties(menu,vo);
            LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Permission::getPid,menu.getId());
            List<Permission> childrens = this.list(queryWrapper);
            vo.setChildrens(childrens);
            vos.add(vo);
        }
        return vos;
    }

    @Override
    public void changeMenu(Long id, Integer type) {
        Permission permission = this.getById(id);
        permission.setDelflag(type);
        this.updateById(permission);
    }


}
