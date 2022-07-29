package com.nott.pms.exception;

import com.nott.pms.common.ResultCode;

public class UserNotFoundExeption extends Exception {
    private static final long serialVersionUID = 111820440373615072L;

    /**
     * 异常代码
     */
    private int code = 401;
    /**
     * 异常消息
     */
    private String msg = "无用户信息";

    public UserNotFoundExeption(ResultCode code) {
        this.code = code.code();
        this.msg = code.msg();
    }

    public UserNotFoundExeption(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public UserNotFoundExeption(String msg) {
        this.msg = msg;
    }

    public UserNotFoundExeption(Throwable cause, int code, String msg) {
        super(cause);
        this.code = code;
        this.msg = msg;
    }

    public UserNotFoundExeption() {
    }

    public int code() {
        return code;
    }

    public String msg() {
        return msg;
    }


}
