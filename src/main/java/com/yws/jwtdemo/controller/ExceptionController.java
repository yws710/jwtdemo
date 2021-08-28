package com.yws.jwtdemo.controller;

import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.yws.jwtdemo.exception.BusinessException;
import com.yws.jwtdemo.exception.StatusCodeEnum;
import com.yws.jwtdemo.util.ApiResult;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局异常处理
 * @author yaoweisong
 *
 */
@Slf4j
@RestControllerAdvice
public class ExceptionController {

    // 捕捉UnauthenticatedException
    @ResponseStatus(HttpStatus.UNAUTHORIZED) // 设置响应结果中Headers下的General下的Status Code属性的值。如果不设置的话，就是200.
    @ExceptionHandler(UnauthenticatedException.class)
    public ApiResult<String> handle401(UnauthenticatedException e) {
    	// 未登录，或者token已过期。
    	log.error("匿名用户访问权限资源时的异常：{}", e.getMessage());
        return ApiResult.fail(StatusCodeEnum.SC401.getCode(), StatusCodeEnum.SC401.getMsg() + ": " + e.getMessage());
    }

    // 捕捉UnauthorizedException
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(UnauthorizedException.class)
    public ApiResult<String> handle403(UnauthorizedException e) {
    	System.out.println("已登录，但是没有访问该资源的权限。");
    	log.error("全局异常信息：{}", e.getMessage());
    	return ApiResult.fail(StatusCodeEnum.SC403.getCode(), StatusCodeEnum.SC403.getMsg() + ": " + e.getMessage());
    }
    
    // 捕捉自定义的业务异常
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BusinessException.class)
    public ApiResult<String> businessExceptionHandle(BusinessException e) {
    	System.out.println("BusinessException异常");
        return ApiResult.fail(e.getCode(), e.getMessage());
    }

    // 捕捉其他所有异常
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResult<String> globalException(Exception e) {
    	System.out.println("捕捉其他所有异常：" + e.getMessage());
    	return ApiResult.fail(StatusCodeEnum.SC500.getCode(), StatusCodeEnum.SC500.getMsg() + ": " + e.getMessage());
    }
}
