package com.nott.pms.emp.mapper;

import com.nott.pms.department.entity.Department;
import com.nott.pms.department.vo.DepartmentVo;
import com.nott.pms.emp.entity.Emp;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nott.pms.emp.vo.EmpInfoVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zzzwlong
 * @since 2022-02-13
 */
public interface EmpMapper extends BaseMapper<Emp> {

    @Select("select tel from emp where id = #{empno}")
    String selectTelByEmpno(Long empno);

    @Select("<script>" +
            "select t.*, b.dept_name deptName,b.dept_local deptLocal from department b RIGHT JOIN (select a.id id,a.deptno deptno,a.manager_no managerNo,a.name name,a.tel tel,a.statu statu,a.entry_time entryTime,a.position position,a.gender gender,a.birthday birthday,es.salary sal from emp a left JOIN emp_sal es on a.id = es.empno where" +
            "<if test='name!=null'> a.name like concat('%',#{name},'%') and</if>" + "<if test='empno!=null'> a.id = ${empno} and</if>" +
            " a.deflag = '0'" +
            ") t on t.deptno = b.id "
            + "</script>")
    List<EmpInfoVo> queryEmpBasicInfo(@Param("name") String name, @Param("empno") Long empNo);


    @Select("select dept.*,c.id managerNo,c.name managerName from department dept left join " +
            "(select e.name,e.id,e.deptno from emp e join (select * from emp where manager_no is NULL and deflag = 0) a on e.deflag=0 and  (e.manager_no = a.id or e.manager_no is NULL)) as c on dept.id = c.deptno ORDER BY dept.id asc")
    List<DepartmentVo> queryDeptManagerNo();
}
