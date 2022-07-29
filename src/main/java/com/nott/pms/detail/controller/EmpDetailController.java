package com.nott.pms.detail.controller;


import com.nott.pms.common.Result;
import com.nott.pms.common.service.SessionService;
import com.nott.pms.consts.SysConstant;
import com.nott.pms.detail.bo.EmpDetailBo;
import com.nott.pms.detail.entity.EmpDetail;
import com.nott.pms.detail.service.IEmpDetailService;
import com.nott.pms.emp.entity.Emp;
import com.nott.pms.exception.UserNotFoundExeption;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 员工详细信息表 前端控制器
 * </p>
 *
 * @author zzzwlong
 * @since 2022-03-29
 */
@RestController
@RequestMapping("/emp/detail")
public class EmpDetailController {
    @Autowired
    private IEmpDetailService empDetailService;
    @Autowired
    private SessionService sessionService;

    @ApiOperation("高级信息")
    @PostMapping("/{id}")
    @ResponseBody
    public Result empDatail(@PathVariable Long id) throws UserNotFoundExeption {
       // sessionService.auth(SysConstant.EMP_PERMISSION);
        return Result.success(empDetailService.queryEmpDetail(id));
    }

    @ApiOperation("员工高级信息修改")
    @PostMapping("/updateById")
    public Result updateById(EmpDetailBo detail) throws UserNotFoundExeption {
//        sessionService.auth(SysConstant.EMP_PERMISSION);
        empDetailService.updateDetail(detail);
        return Result.ok();
    }

    @ApiOperation("新增员工")
    @PostMapping("/add")
    public Result empAdd(Emp info,EmpDetail advInfo) throws BadHanyuPinyinOutputFormatCombination {
        empDetailService.saveNewEmp(info,advInfo);
        return Result.ok();
    }

    @ApiOperation("新增信息")
    @PostMapping("/addDetail")
    public Result addDetail(EmpDetail advInfo) throws BadHanyuPinyinOutputFormatCombination {
        empDetailService.addDetail(advInfo);
        return Result.ok();
    }



}
