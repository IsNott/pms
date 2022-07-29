package com.nott.pms.department.controller;


import com.nott.pms.common.Result;
import com.nott.pms.common.service.SessionService;

import com.nott.pms.consts.SysConstant;
import com.nott.pms.department.bo.DepartmentBo;
import com.nott.pms.department.entity.Department;
import com.nott.pms.department.service.IDepartmentService;
import com.nott.pms.exception.BusinessException;
import com.nott.pms.exception.UserNotFoundExeption;
import com.nott.pms.menu.service.IPermissionService;
import com.nott.pms.menu.service.IRolePermissionService;
import com.nott.pms.user.entity.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zzzwlong
 * @since 2022-02-28
 */
@RestController
@RequestMapping("/pms/department")
public class DepartmentController {


    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private IPermissionService permissionService;


    @ApiOperation("按条件查询部门")
    @PostMapping("/deptInfo")
    public Result deptInfo(Integer page, Integer size, String deptId, String name) throws UserNotFoundExeption {
//        sessionService.auth(SysConstant.DEPT_PERMISSION);
        return Result.success(departmentService.queryDeptInfo(page, size, deptId, name));
    }

    @ApiOperation("添加部门")
    @PostMapping("/addDept")
    public Result addDept(DepartmentBo departmentBo) {
        departmentService.saveDept(departmentBo);
        return Result.ok();
    }

    @PostMapping("/deptList")
    @ApiOperation("/部门列表")
    public Result deptLIst() {
        return Result.success(departmentService.list());
    }

    @ApiOperation("查询部门信息")
    @PostMapping("/queryDept")
    public Result queryDept() {
        return Result.success(departmentService.queryDeptList());
    }

    @ApiOperation("删除部门")
    @PostMapping("/remove")
    public Result remove(Long id) {
        departmentService.removeById(id);
        return Result.ok();
    }

    @ApiOperation("迁出员工")
    @PostMapping("/moveEmp")
    public Result moveEmp(Long deptId, Long empNo) throws UserNotFoundExeption {
//        sessionService.auth(SysConstant.DEPT_PERMISSION);
        departmentService.moveEmp(deptId, empNo);
        return Result.ok();
    }

    @ApiOperation("更新部门")
    @PostMapping("/update")
    public Result update(Department department) {
        try {
            departmentService.updateById(department);
            return Result.ok();
        } catch (Exception e) {
            return Result.failure("更新出错");
        }
    }


}
