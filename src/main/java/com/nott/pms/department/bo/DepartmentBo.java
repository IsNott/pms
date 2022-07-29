package com.nott.pms.department.bo;

import lombok.Data;

@Data
public class DepartmentBo {
    private String DeptName;
    private String deptLocal;
    private Long manager;
}
