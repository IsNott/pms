package com.nott.pms.exception;

import com.nott.pms.common.Result;

import io.jsonwebtoken.ExpiredJwtException;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
//import netscape.security.ForbiddenTargetException;
import org.apache.ibatis.javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 统一异常处理类
 *
 * @Author zzzwlong
 * @Date 2022/1/4 19:27
 */
@ControllerAdvice
public class MyExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(MyExceptionHandler.class);

    /**
     * 缺少Token
     * @param e
     * @param request
     * @return
     */
//    @ExceptionHandler(TokenValidationException.class)
//    @ResponseBody
//    public Result handleTokenException(TokenValidationException e,HttpServletRequest request){
//        logger.error(request.getRequestURI() + ":Token Missing",e);
//        return Result.failure("缺少Token");
//    }

//    @ExceptionHandler(UsernameNotFoundException.class)
//    @ResponseBody
//    public Result handleUsernameNotFoundException(TokenValidationException e,HttpServletRequest request){
//        logger.error(request.getRequestURI() + ":User not found",e);
//        return Result.failure("用户信息不存在");
//    }

//    /**
//     * Token过期
//     * @param e
//     * @param request
//     * @return
//     */
//    @ExceptionHandler(ExpiredJwtException.class)
//    @ResponseBody
//    public Result handleExpiredJwtException(ExpiredJwtException e,HttpServletRequest request){
//        logger.error(request.getRequestURI()+"Token过期",e);
//        return Result.failure("Token已过期");
//
//    }

    /**
     * 参数解析失败异常处理
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public Result handleHttpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request) {
        logger.error(request.getRequestURI() + ":参数解析失败", e);
        return Result.failure(400, "参数解析失败");
    }


    /**
     * 不支持当前请求方法异常处理
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public Result handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        logger.error(request.getRequestURI() + ":不支持当前请求方法", e);
        return Result.failure(405, "不支持当前请求方法");
    }


    /**
     * 项目运行异常处理
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Result handleException(Exception e, HttpServletRequest request) {
        logger.error(request.getRequestURI() + ":服务运行异常", e);
        return Result.failure(999, e.getMessage());
    }

    /**
     * 权限异常
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UserNotFoundExeption.class)
    @ResponseBody
    public Result forbiddenException(Exception e, HttpServletRequest request) {
        logger.error(request.getRequestURI() + ":无权限", e);
        return Result.failure(401, "暂无访问权限");
    }

    /**
     * 无相关资源异常
     *
     * @param e
     * @param request
     * @return
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public Result NotFoundException(NotFoundException e, HttpServletRequest request) {
        logger.error(request.getRequestURI() + ":无权限", e);
        return new Result(404, "无相关资源");
    }

    /**
     * 自定义异常处理
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public Result handleException(BusinessException e, HttpServletRequest request) {
        logger.error(request.getRequestURI() + ":自定义内部异常：" + e.msg(), e.msg());
        return new Result(e.code(), e.msg());

    }

    /**
     * 自定义异常处理
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(IOException.class)
    @ResponseBody
    public Result handleException(IOException e, HttpServletRequest request) {
        logger.error(request.getRequestURI() + ":自定义内部异常：" + e.getMessage());
        return new Result(-999,e.getMessage());

    }

    @ExceptionHandler(BadHanyuPinyinOutputFormatCombination.class)
    @ResponseBody
    public Result handleException(BadHanyuPinyinOutputFormatCombination e, HttpServletRequest request) {
        logger.error(request.getRequestURI() + ":自定义内部异常：" + e.getMessage(), e.getMessage());
        return new Result(-999, "拼音错误");

    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result Exception(Exception e, HttpServletRequest request) {
        logger.error(request.getRequestURI() + ":服务器内部异常：" + e.getMessage(), e.getMessage());
        return new Result(-999, e.getMessage());

    }


}
