package com.nott.pms.menu.mapper;

import com.nott.pms.menu.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author zzzwlong
 * @since 2022-02-07
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    @Select("SELECT\n" +
            "\t* \n" +
            "FROM\n" +
            "\tpermission p \n" +
            "WHERE\n" +
            "\tid IN ( SELECT permission_id FROM role_permission rp WHERE rp.role_id = #{roleId} AND delflag = 0 ) and (pid = '' or pid = null) \n" +
            "ORDER BY\n" +
            "id asc")
    List<Permission> selectPemission(@Param("roleId") Long roleId);

    @Select("select * from permission p where p.pid = #{id} and p.delflag = 0")
    List<Permission> selectChildrenMenu(@Param("id")String id);

    @Select("select permission_value pv from permission p where p.id = #{id} and permission_value = #{perValue} and p.delflag = 0 ")
    String selectPerValueByPerIdandValue(@Param("id")String id,@Param("perValue") String perValue);
}
