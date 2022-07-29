//package com.nott.pms.exception;
//
///**
// * Token missing时异常
// *
// * @Author zzzwlong
// * @Date 2022/1/8
// */
//public class TokenValidationException extends RuntimeException {
//    private static final long serialVersionUID = 111820440373615072L;
//
//    /**
//     * 异常代码
//     */
//    private int code = -999;
//    /**
//     * 异常消息
//     */
//    private String msg = "Token Missing";
//
//    public TokenValidationException(String msg) {
//        super(msg);
//    }
//
//    public TokenValidationException() {
//
//    }
//
//    public TokenValidationException(int code, String msg) {
//        this.code = code;
//        this.msg = msg;
//    }
//
//    public TokenValidationException(String message, int code, String msg) {
//        super(message);
//        this.code = code;
//        this.msg = msg;
//    }
//
//    public TokenValidationException(String message, Throwable cause, int code, String msg) {
//        super(message, cause);
//        this.code = code;
//        this.msg = msg;
//    }
//
//    public TokenValidationException(Throwable cause, int code, String msg) {
//        super(cause);
//        this.code = code;
//        this.msg = msg;
//    }
//
//    public TokenValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code, String msg) {
//        super(message, cause, enableSuppression, writableStackTrace);
//        this.code = code;
//        this.msg = msg;
//    }
//}
