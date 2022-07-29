package com.nott.pms.department.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nott.pms.common.Result;
import com.nott.pms.consts.Consts;
import com.nott.pms.consts.SysConstant;
import com.nott.pms.department.bo.DepartmentBo;
import com.nott.pms.department.entity.Department;
import com.nott.pms.department.mapper.DepartmentMapper;
import com.nott.pms.department.service.IDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nott.pms.department.vo.DepartmentVo;
import com.nott.pms.department.vo.DeptInfoVo;
import com.nott.pms.emp.entity.Emp;
import com.nott.pms.emp.mapper.EmpMapper;
import com.nott.pms.emp.service.IEmpService;
import com.nott.pms.exception.BusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zzzwlong
 * @since 2022-02-28
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {
    @Autowired
    private IEmpService empService;
    @Autowired
    private EmpMapper empMapper;

    @Override
    public Page<DeptInfoVo> queryDeptInfo(Integer pageNum, Integer size, String deptId, String name) {
        QueryWrapper<Emp> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Emp::getDeflag, SysConstant.delflag.NORMAL)
                .eq(Emp::getDeptno, deptId);
        List<Emp> empList = empService.list(wrapper);
        QueryWrapper<Department> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(!StringUtils.isEmpty(deptId), Department::getId, deptId)
                .like(!StringUtils.isEmpty(name), Department::getDeptName, name);
        Page<DeptInfoVo> page = new Page<>();
        List<Department> departments = this.list(queryWrapper);
        List<DeptInfoVo> list = new ArrayList<>();
        for (Department department : departments) {
            DeptInfoVo vo = new DeptInfoVo();
            BeanUtil.copyProperties(department, vo);
            vo.setEmps(empList);
            list.add(vo);
        }
        if (!Objects.isNull(pageNum)) {
            page.setCurrent(pageNum);
        }
        if (!Objects.isNull(size)) {
            page.setSize(size);
        }
        page.setRecords(list);
        return page;
    }

    @Override
    public List<DepartmentVo> queryDeptList() {
        List<DepartmentVo> departmentVos = empMapper.queryDeptManagerNo();
        for (DepartmentVo departmentVo : departmentVos) {
            LambdaQueryWrapper<Emp> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Emp::getDeflag, SysConstant.delflag.NORMAL)
                    .eq(Emp::getDeptno, departmentVo.getId());
            List<Emp> empList = empService.list(wrapper);
            departmentVo.setEmpList(empList);
        }
        return departmentVos;
    }

    @Override
    public Emp queryManagerByNo(Long dept) throws BusinessException {
        if (dept == null) {
            throw new BusinessException("部门id不能为空");
        }
        Department department = this.getById(dept);
        LambdaQueryWrapper<Emp> wrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<Emp> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.isNull(Emp::getManagerNo).eq(Emp::getDeflag, SysConstant.delflag.NORMAL);
        Emp boss = empService.list(queryWrapper).get(0);
        wrapper.eq(Emp::getDeptno, department.getId())
                .eq(Emp::getDeflag, SysConstant.delflag.NORMAL)
                .eq(Emp::getManagerNo, boss.getId());
        List<Emp> empList = empService.list(wrapper);
        if (empList.size() <= 0) {
            return null;
        }
        return empList.get(0);
    }

    @Override
    public void moveEmp(Long deptId, Long empNo) {
        Assert.notNull(deptId, "部门id不能为空");
        Assert.notNull(empNo, "员工id不能为空");
        Emp emp = empService.getById(empNo);
        if (emp.getManagerNo() == null) {
            throw new BusinessException("总经理或部门经理不支持手动迁出，请联系人事部门");
        }
        Emp manager = queryManagerByNo(emp.getDeptno());

        if (empNo.equals(manager.getId())) {
            throw new BusinessException("此员工是部门经理，不能迁出！");
        }
        if (emp.getDeptno().equals(deptId)) {
            throw new BusinessException("此员工已在该部门！");
        }
        Department department = this.getById(deptId);
        emp.setDeptno(department.getId());
        emp.setManagerNo(queryManagerByNo(deptId).getId());
        empService.updateById(emp);

    }

    @Override
    public void saveDept(DepartmentBo departmentBo) {
        Assert.notNull(departmentBo.getManager(), "部门经理编号不能为空");
        Department department = new Department();
        department.setDeptLocal(departmentBo.getDeptLocal());
        department.setDeptName(departmentBo.getDeptName());
        this.save(department);
        Long managerNo = departmentBo.getManager();
        Emp emp = empService.getById(managerNo);
        LambdaQueryWrapper<Emp> wrapper = new LambdaQueryWrapper<>();
        wrapper.isNull(Emp::getManagerNo);
        Emp boss = empService.list(wrapper).get(0);
        emp.setManagerNo(boss.getId());
        emp.setDeptno(department.getId());
        empService.updateById(emp);
    }


}
