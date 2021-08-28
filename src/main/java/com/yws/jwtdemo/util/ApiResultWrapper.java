package com.yws.jwtdemo.util;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yws.jwtdemo.exception.BusinessException;

/**
 * 拦截Controller方法的返回值，统一处理返回值/响应体
 * @author yaoweisong
 *
 */
@RestControllerAdvice
public class ApiResultWrapper implements ResponseBodyAdvice<Object> {
	private ObjectMapper om = new ObjectMapper();
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		// 是否支持advice功能
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		// 对返回的数据进行处理
		if (body instanceof String) {
			try {
				return om.writeValueAsString(ApiResult.success(body));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				throw new BusinessException(-1, "json解析出错");
			}
		}
		
		// 这个判断的作用：防止全局异常处理后返回的结果(类型为ApiResult)再次被包装。
		if (body instanceof ApiResult) {
			return body;
		}
		return ApiResult.success(body);
	}

}
