package com.nott.pms.detail.vo;

import com.nott.pms.detail.entity.EmpDetail;
import lombok.Data;

import java.util.Date;

/**
 * @Author zzzwlong
 * @Date 2022/4/5 16:46
 */
@Data
public class EmpDetailVo extends EmpDetail {
    private String name;
    private String tel;
    private Date birthday;
    private Date entryTime;
    private Date endTime;
    private Integer ages;
    private String position;
}
