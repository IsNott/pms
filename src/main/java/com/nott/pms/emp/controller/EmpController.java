package com.nott.pms.emp.controller;


import com.nott.pms.common.Result;
import com.nott.pms.common.service.SessionService;
import com.nott.pms.consts.SysConstant;
import com.nott.pms.department.service.IDepartmentService;
import com.nott.pms.emp.bo.EmpBo;
import com.nott.pms.emp.entity.Emp;
import com.nott.pms.emp.service.IEmpService;
import com.nott.pms.exception.BusinessException;
import com.nott.pms.exception.UserNotFoundExeption;
import com.nott.pms.menu.service.IPermissionService;
import com.nott.pms.user.entity.User;
import com.nott.pms.user.service.IUserService;
import io.swagger.annotations.ApiOperation;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.util.Date;

import static com.nott.pms.consts.Consts.CURRENT_USER;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zzzwlong
 * @since 2022-02-13
 */
@RestController
@RequestMapping("/emp")
public class EmpController {

    @Autowired
    private IEmpService empService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IDepartmentService departmentService;

    @RequestMapping("/basic")
    @ApiOperation("查看员工基本信息")
    public Result basicInfo(Integer page, Integer size, String name) throws UserNotFoundExeption {

        return Result.success(empService.findByPage(page, size, name));
    }

    @PostMapping("/updateBasic")
    @ApiOperation("更新员工基本信息")
    @ResponseBody
    public Result updateBasic(EmpBo info) {
        empService.saveBascie(info);
        return Result.ok();
    }

    @PostMapping("/list")
    @ApiOperation("查看列表")
    public Result List() {
        return Result.success(empService.list());
    }

    @PostMapping("/queryEmp")
    @ApiOperation("员工列表")
    public Result queryEmp(Long page, Long size, String name) throws UserNotFoundExeption {
//        sessionService.auth(SysConstant.EMP_PERMISSION);
        return Result.success(empService.queryEmpByPage(page, size, name));
    }

    @PostMapping("/remove")
    @ApiOperation("删除")
    public Result remove(Long id) {
        try {
            Emp emp = empService.getById(id);
            emp.setDelTime(new Date());
            empService.updateById(emp);
            empService.removeById(emp);
            userService.removeByEmpno(emp.getId());
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        return Result.ok();
    }

    @PostMapping("/initNomEmp")
    @ApiOperation("查询非经理的员工")
    public Result initNomEmp() throws UserNotFoundExeption {
        //sessionService.auth(SysConstant.DEPT_PERMISSION);
        return Result.success(empService.queryNomemp());
    }

    @PostMapping("/add")
    public Result add(Emp emp) {
        Emp manager = departmentService.queryManagerByNo(emp.getDeptno());
        if (manager != null) {
            emp.setManagerNo(manager.getId());
        }
        empService.save(emp);
        Emp id = empService.getById(emp);
        return Result.success(id);
    }


}
