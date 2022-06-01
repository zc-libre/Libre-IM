package com.libre.im.security.config;

import com.libre.core.result.R;
import com.libre.core.result.ResultCode;
import com.libre.core.toolkit.Exceptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author: Libre
 * @Date: 2022/5/30 11:46 PM
 */
@Slf4j
@RestControllerAdvice
public class LibreIMExceptionAdvice {

	@ExceptionHandler(BadCredentialsException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public R<Object> handleError(BadCredentialsException e) {
		log.error("密码错误: {}", Exceptions.getStackTraceAsString(e));
		String message = String.format("%s", e.getMessage());
		return R.fail(ResultCode.UN_AUTHORIZED, message);
	}

}
