package com.yws.jwtdemo.util;

import java.io.Serializable;

import com.yws.jwtdemo.exception.StatusCodeEnum;

import lombok.Getter;
import lombok.Setter;

/**
 * 封装返回结果
 * @author yaoweisong
 *
 * @param <T>
 */
@Getter
@Setter
public class ApiResult<T> implements Serializable {
	private static final long serialVersionUID = -3262186527338840277L;
	private Integer code;
	private String msg;
	private T data;
	
	public static <T> ApiResult<T> success(T data) {
		return ApiResult.success(StatusCodeEnum.SC200.getMsg(), data);
	}
	
	public static <T> ApiResult<T> success(String msg, T data) {
		ApiResult<T> apiResult = new ApiResult<T>();
		apiResult.setCode(StatusCodeEnum.SC200.getCode());
		apiResult.setMsg(msg);
		apiResult.setData(data);
		return apiResult;
	}
	
	public static <T> ApiResult<T> fail(Integer code, String msg) {
		ApiResult<T> apiResult = new ApiResult<T>();
		apiResult.setCode(code);
		apiResult.setMsg(msg);
		return apiResult;
	}
}
