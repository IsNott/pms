package com.nott.pms.common;

/**
 * 枚举一下状态码
 * @Author zzzwlong
 * @Date 2022/1/3 12:17
 */
public enum ResultCode {

    /**
     * 成功状态码
     */
    OK(000,"成功"),

    /**
     * 失败状态码
     */
    FAILD(-999,"失败"),

    /**
     * 请求参数有误
     */
    INCORRECT_REQ_PARAM(201, "请求参数有误"),
    /**
     * 未经授权
     */
    UNAUTHORIZED(401, "未经授权"),
    /**
     * 内部服务错误
     */
    INTERNAL_SERVICE_ERRORS(500, "内部服务错误"),
    /**
     * 未知异常
     */
    UNKOWN_ERROR(999,"未知异常");



    private Integer code;

    private String msg;

    ResultCode() {
    }

    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer code() {
        return code;
    }


    public String msg() {
        return msg;
    }


    public static String getMsg(String name){
        for (ResultCode item : ResultCode.values()) {
            if (item.name().equals(name)) {
                return item.msg;
            }
        }
        return name;
    }

    public static Integer getCode(String name) {
        for (ResultCode item : ResultCode.values()) {
            if (item.name().equals(name)) {
                return item.code;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
