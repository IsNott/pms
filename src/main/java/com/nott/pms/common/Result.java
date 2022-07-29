package com.nott.pms.common;

import java.io.Serializable;

/**
 * 通用返回结果集
 * @Author zzzwlong
 * @Date 2022/1/3 12:09
 */

public class Result<T> implements Serializable {

    public static final Long VersionUID = 1L;

    private Integer code;
    private String msg;
    private T data;



    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setResultCode(ResultCode code) {
        this.code = code.code();
        this.msg = code.msg();
    }

    public Result(Integer code,String msg){
        this.code=code;
        this.msg=msg;
    }



    public Result() {
    }

    /**
     * 成功，不返回访问数据
     * @return
     */
    public static Result ok(){
        Result<Object> result = new Result<>();
        result.setResultCode(ResultCode.OK);
        return result;
    }

    /**
     * 成功,并且加上返回数据
     * @param data
     * @return
     */
    public static Result success(Object data) {
        Result result = new Result();
        result.setResultCode(ResultCode.OK);
        result.setData(data);
        return result;
    }

    /**
     * 成功,加上返回信息和数据
     * @param data
     * @return
     */
    public static Result success(String msg,Object data) {
        Result result = new Result();
        result.setResultCode(ResultCode.OK);
        result.setData(data);
        result.setMsg(msg);
        return result;
    }

    /**
     * 成功 自定义成功返回状态 加上数据
     * @param resultCode
     * @param data
     * @return
     */
    public static Result success(ResultCode resultCode, Object data) {
        Result result = new Result();
        result.setResultCode(resultCode);
        result.setData(data);
        return result;
    }

    /**
     * 只返回失败的状态码
     * @param resultCode
     * @return
     */
    public static Result failure(ResultCode resultCode) {
        Result result = new Result();
        result.setResultCode(resultCode.FAILD);
        return result;
    }

    public static Result failure(String msg) {
        Result result = new Result();
        result.setMsg(msg);
        return result;
    }

    /**
     * 返回失败的状态码及数据
     * @param resultCode
     * @param data
     * @return
     */
    public static Result failure(ResultCode resultCode, Object data) {
        Result result = new Result();
        result.setResultCode(resultCode.FAILD);
        result.setData(data);
        return result;
    }

    public static Result failure(Integer code,String msg) {
        Result result = new Result();
        result.setMsg(msg);
        result.setCode(code);
        return result;
    }

    public static Result failure() {
        Result result = new Result();
        result.setMsg("error");
        result.setCode(-999);
        return result;
    }

}
