package com.nott.pms.user.entity;

import lombok.Data;

@Data
public class UserLoginParam {

    private String username;
    private String password;
    private String code;
}
