package com.nott.pms.detail.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nott.pms.common.service.SessionService;
import com.nott.pms.consts.SysConstant;
import com.nott.pms.department.service.IDepartmentService;
import com.nott.pms.detail.bo.EmpDetailBo;
import com.nott.pms.detail.entity.EmpDetail;
import com.nott.pms.detail.mapper.EmpDetailMapper;
import com.nott.pms.detail.service.IEmpDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nott.pms.detail.vo.EmpDetailVo;
import com.nott.pms.emp.entity.Emp;
import com.nott.pms.emp.service.IEmpService;
import com.nott.pms.exception.BusinessException;
import com.nott.pms.exception.UserNotFoundExeption;
import com.nott.pms.sal.entity.EmpSal;
import com.nott.pms.sal.service.IEmpSalService;
import com.nott.pms.user.entity.User;
import com.nott.pms.user.service.IUserService;
import com.nott.pms.utils.PinyinUtil;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * <p>
 * 员工详细信息表 服务实现类
 * </p>
 *
 * @author zzzwlong
 * @since 2022-03-29
 */
@Service
public class EmpDetailServiceImpl extends ServiceImpl<EmpDetailMapper, EmpDetail> implements IEmpDetailService {

    @Autowired
    private IEmpService empService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private IEmpSalService empSalService;

    @Override
    public EmpDetailVo queryEmpDetail(Long id) {
        LambdaQueryWrapper<EmpDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EmpDetail::getEmpNo, id);
        EmpDetail detail = this.list(wrapper).get(0);
        Emp emp = empService.getById(detail.getEmpNo());
        EmpDetailVo vo = new EmpDetailVo();
        BeanUtils.copyProperties(detail, vo);
        vo.setBirthday(emp.getBirthday());
        vo.setEntryTime(emp.getEntryTime());
        Date date = emp.getEntryTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(GregorianCalendar.YEAR, detail.getContractYears());
        vo.setEndTime(calendar.getTime());
        vo.setName(emp.getName());
        vo.setTel(emp.getTel());
        Calendar cal = Calendar.getInstance();//当前时间
        //当前的年月日
        int yearNow = cal.get(Calendar.YEAR);  //年
        int monthNow = cal.get(Calendar.MONTH);  //月
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //日
        cal.setTime(emp.getBirthday());//出生时间Date
        //出生的年月日
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth)
                    age--;
            } else {
                age--;
            }

        }
        vo.setAges(age);
        vo.setPosition(emp.getPosition());
        return vo;
    }

    @Override
    public void updateDetail(EmpDetailBo detail) {
        String position = detail.getPosition();
        Emp emp = empService.getById(detail.getEmpNo());
        emp.setPosition(position);
        empService.updateById(emp);
        this.updateById(detail);
    }

    @Override
    @Transactional
    public void saveNewEmp(Emp emp, EmpDetail detail) throws BadHanyuPinyinOutputFormatCombination {
//        try {
//            Emp manager = departmentService.queryManagerByNo(emp.getDeptno());
//            emp.setManagerNo(manager.getId());
//            empService.save(emp);
//            Emp newEmp = empService.getById(emp);
//            detail.setEmpNo(newEmp.getId());
//            this.save(detail);
//            User user = new User();
//            user.setTel(emp.getTel());
//            user.setEmpNo(emp.getId());
//            user.setNickName(emp.getName());
//            user.setUsername(PinyinUtil.toPinYin(emp.getName()));
//            user.setPassword("123456");
//            user.setCreateTime(emp.getEntryTime());
//            user.setUpdateTime(new Date());
//            userService.save(user);
//        } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
//            throw new BusinessException("新增失败");
//        }
    }

    @Override
    public void addDetail(EmpDetail empDetail) throws BadHanyuPinyinOutputFormatCombination {
        try {
            this.save(empDetail);
            Emp emp = empService.getById(empDetail.getEmpNo());
            User user = new User();
            user.setTel(emp.getTel());
            user.setEmpNo(emp.getId());
            user.setNickName(emp.getName());
//            user.setUsername(PinyinUtil.toPinYin(emp.getName(),null));
            user.setUsername(empDetail.getIdCard().substring(10));
            user.setPassword("123456");
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            userService.save(user);
            EmpSal sal = new EmpSal();
            sal.setEmpno(String.valueOf(user.getEmpNo()));
            sal.setSalary(3600.0);
            sal.setSubsidy(0.0);
            sal.setOtSal(20.0);
            empSalService.save(sal);
        } catch (Exception e) {
            throw new BusinessException("新增失败");
        }
    }
}
