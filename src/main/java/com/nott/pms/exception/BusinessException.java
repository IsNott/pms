package com.nott.pms.exception;

import com.nott.pms.common.ResultCode;

/**
 *
 * 自定义异常类
 * @Author zzzwlong
 * @Date 2021/12/20 16:30
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 111820440373615072L;

    /**
     * 异常代码
     */
    private int code = -999;
    /**
     * 异常消息
     */
    private String msg = "错误";

    public BusinessException(ResultCode code) {
        this.code = code.code();
        this.msg = code.msg();
    }

    public BusinessException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public BusinessException(String msg) {
        this.msg = msg;
    }

    public BusinessException(Throwable cause, int code, String msg) {
        super(cause);
        this.code = code;
        this.msg = msg;
    }

    public BusinessException() {
    }

    public int code() {
        return code;
    }

    public String msg() {
        return msg;
    }


}
