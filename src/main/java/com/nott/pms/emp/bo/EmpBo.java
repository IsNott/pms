package com.nott.pms.emp.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author zzzwlong
 * @Date 2022/4/5 11:58
 */
@Data
public class EmpBo {
    private Long id;
    private String gender;
    private Long deptno; //部门编号
    private String statu;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date entryTime;
    private String tel;
    private String name;
}
