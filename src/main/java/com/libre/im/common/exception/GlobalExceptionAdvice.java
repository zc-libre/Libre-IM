package com.libre.im.common.exception;

import com.libre.boot.exception.ErrorUtil;
import com.libre.toolkit.exception.LibreException;
import com.libre.toolkit.result.R;
import com.libre.toolkit.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author: Libre
 * @Date: 2022/8/26 11:02 PM
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

	@ExceptionHandler(LockedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public R<Object> handleError(LockedException e) {
		return R.fail(ResultCode.UN_AUTHORIZED, e.getMessage());
	}

	@ExceptionHandler(LibreException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public R<Object> handle(LibreException e) {
		ErrorUtil.publishEvent(e);
		return R.fail(ResultCode.FAILURE, e.getMessage());
	}

}
