package com.nott.pms.user.vo;

import com.nott.pms.user.entity.User;
import lombok.Data;

/**
 * @Author zzzwlong
 * @Date 2022/4/5 20:46
 */
@Data
public class UserVo extends User {
    private String empName;
    private String roleName;
}
