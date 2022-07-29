package com.nott.pms.department.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nott.pms.common.Result;
import com.nott.pms.department.bo.DepartmentBo;
import com.nott.pms.department.entity.Department;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nott.pms.department.vo.DepartmentVo;
import com.nott.pms.department.vo.DeptInfoVo;
import com.nott.pms.emp.entity.Emp;
import com.nott.pms.exception.BusinessException;


import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzzwlong
 * @since 2022-02-28
 */
public interface IDepartmentService extends IService<Department> {

    Page<DeptInfoVo> queryDeptInfo(Integer page, Integer size,String deptId,String name);

    List<DepartmentVo> queryDeptList();

    Emp queryManagerByNo(Long dept) throws BusinessException;

    void moveEmp(Long deptId, Long empNo);

    void saveDept(DepartmentBo departmentBo);
}
