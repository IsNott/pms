package com.nott.pms.emp.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nott.pms.consts.SysConstant;
import com.nott.pms.department.service.IDepartmentService;
import com.nott.pms.emp.bo.EmpBo;
import com.nott.pms.emp.entity.Emp;
import com.nott.pms.emp.mapper.EmpMapper;
import com.nott.pms.emp.service.IEmpService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nott.pms.emp.vo.EmpInfoVo;
import com.nott.pms.exception.BusinessException;
import com.nott.pms.menu.service.IPermissionService;
import com.nott.pms.user.entity.User;
import com.nott.pms.user.service.IUserService;
import com.nott.pms.utils.PinyinUtil;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zzzwlong
 * @since 2022-02-13
 */
@Service
@Slf4j
public class EmpServiceImpl extends ServiceImpl<EmpMapper, Emp> implements IEmpService {

    @Autowired
    private IUserService userService;
    @Autowired
    private IDepartmentService departmentService;


    @Override
    public Page<EmpInfoVo> findByPage(Integer page, Integer size, String name) {
        List<EmpInfoVo> list = baseMapper.queryEmpBasicInfo(name, null);
        Page<EmpInfoVo> empPage = new Page<>();
        if (!CollectionUtils.isEmpty(list)) {
            empPage.setCurrent(page);
            empPage.setSize(size);
            empPage.getRecords();
            empPage.setRecords(list);
            empPage.setTotal(list.size());
        }
        log.info("emp：" + empPage);
        return empPage;
    }


    @Override
    public Page<Emp> queryEmpByPage(Long page, Long rows, String name) {
        Page<Emp> empPage = new Page<>();
        if (Objects.nonNull(page) && page > 0) {
            empPage.setCurrent(page);
        }
        if (Objects.nonNull(rows) && rows > 0) {
            empPage.setSize(rows);
        }
        QueryWrapper<Emp> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Emp::getDeflag, SysConstant.delflag.NORMAL)
                .like(StringUtils.hasText(name), Emp::getName, name).orderByAsc(Emp::getId);
        return this.page(empPage, wrapper);
    }

    @Override
    public void saveBascie(EmpBo info) {
        Emp emp = this.getById(info.getId());
        String infoTel = info.getTel();
        String infoName = info.getName();
        Long infoDeptno = info.getDeptno();
        String gender = info.getGender();
        String infoStatu = info.getStatu();
        Date infoEntryTime = info.getEntryTime();
        Date infoBirthday = info.getBirthday();
        if (null == emp) {
            throw new BusinessException("id不能为空");
        }
        if (StringUtils.hasText(infoName) && !infoName.equals(emp.getName())) {
            emp.setName(info.getName());
        }
        if (StringUtils.hasText(info.getTel()) && !infoTel.equals(emp.getTel())) {
            emp.setTel(info.getTel());
        }
        if (Objects.nonNull(infoDeptno) && !(infoDeptno.equals(info.getDeptno()))) {
            emp.setDeptno(infoDeptno);
            Emp manager = departmentService.queryManagerByNo(infoDeptno);
            if (manager != null && (manager.getId() != emp.getId())) {
                emp.setManagerNo(manager.getId());
            }
        }
        if (Objects.nonNull(infoStatu) && !infoStatu.equals(emp.getStatu())) {
            if ((infoStatu.equals("出勤") || infoStatu.equals("0"))) {
                emp.setStatu(0);
            } else emp.setStatu(1);
        }
        if (Objects.nonNull(gender)) {
            if (gender.equals("男") || gender.equals("1")) {
                emp.setGender("1");
            } else emp.setGender("0");
        }
        if (Objects.nonNull(infoEntryTime) && !infoEntryTime.equals(emp.getEntryTime())) {
            emp.setEntryTime(infoEntryTime);
        }
        if (Objects.nonNull(infoBirthday) && !infoBirthday.equals(emp.getBirthday())) {
            emp.setBirthday(infoBirthday);
        }
        this.updateById(emp);
    }

    @Override
    public List<Emp> queryNomemp() {
        LambdaQueryWrapper<Emp> wrapper = new LambdaQueryWrapper<>();
        wrapper.isNull(Emp::getManagerNo);
        Emp manager = this.list(wrapper).get(0); //查询总经理
        LambdaQueryWrapper<Emp> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(Emp::getManagerNo, manager.getId()).eq(Emp::getDeflag, SysConstant.delflag.NORMAL)
                .ne(Emp::getId, manager.getId());
        List<Emp> empList = this.list(queryWrapper); //查出所有普通员工
        return empList;

    }


}
