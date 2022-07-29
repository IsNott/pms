package com.nott.pms.department.vo;

import com.nott.pms.department.entity.Department;
import com.nott.pms.emp.entity.Emp;
import lombok.Data;

import java.util.List;

@Data
public class DeptInfoVo extends Department {
    List<Emp> emps;
}
