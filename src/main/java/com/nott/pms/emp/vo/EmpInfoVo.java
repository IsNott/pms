package com.nott.pms.emp.vo;

import cn.hutool.core.date.DateTime;
import com.nott.pms.emp.entity.Emp;
import lombok.Data;

import java.util.Date;

/**
 * @Author zzzwlong
 * @Date 2022/2/26 22:31
 */
@Data
public class EmpInfoVo  {
    private String id;
    private String deptno;
    private String managerNo;
    private String name;
    private String tel;
    private String statu;
    private Date entryTime;
    private String position;
    private String gender;
    private Date birthday;
    private String deptName;
    private String deptLocal;
    private String sal;
}
