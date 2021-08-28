package com.yws.jwtdemo.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = 4945120500710518263L;
	
	private Integer code;
	
	/**
	 * 使用已有的错误类型
	 * @param type 枚举类中的错误类型
	 */
	public BusinessException(StatusCodeEnum type) {
		super(type.getMsg());
		this.code = type.getCode();
	}
	
	/**
	 * 自定义错误类型
	 * @param code 自定义的错误码
	 * @param msg 自定义的错误提示信息
	 */
	public BusinessException(Integer code, String msg) {
		super(msg);
		this.code = code;
	}
}
