package com.nott.pms.department.vo;

import com.nott.pms.emp.entity.Emp;
import lombok.Data;

import java.util.List;

/**
 * 部门信息页面对象
 */
@Data
public class DepartmentVo {
    private Long id;
    private String deptName;
    private String deptLocal;
    private Long managerNo;
    private String managerName;
    private List<Emp> empList;
}
