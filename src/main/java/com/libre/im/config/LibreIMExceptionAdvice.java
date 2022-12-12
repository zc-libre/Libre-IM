package com.libre.im.config;

import com.libre.toolkit.result.R;
import com.libre.toolkit.result.ResultCode;
import com.libre.toolkit.core.Exceptions;
import com.libre.im.websocket.exception.LibreImException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
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
		log.error("认证失败: {}", Exceptions.getStackTraceAsString(e));
		String message = String.format("%s", e.getMessage());
		return R.fail(ResultCode.UN_AUTHORIZED, message);
	}

	@ExceptionHandler(LockedException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public R<Object> handleError(LockedException e) {
		String message = String.format("%s", e.getMessage());
		return R.fail(ResultCode.UN_AUTHORIZED, message);
	}

	@ExceptionHandler(LibreImException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public R<Object> handleError(LibreImException e) {
		String message = String.format("%s", e.getMessage());
		return R.fail(ResultCode.INTERNAL_SERVER_ERROR, message);
	}

}
