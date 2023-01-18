package com.libre.im.common.result;

import com.libre.toolkit.result.IResultCode;
import lombok.AllArgsConstructor;

/**
 * @author Libre
 * @date 2022/1/1 18:56
 */

@AllArgsConstructor
public enum ApiCode implements IResultCode {

	USER_NOT_EXIST(20001, "用户不存在"), USER_ALREADY_EXIST(20002, "用户已经存在");

	private final Integer code;

	private final String message;

	@Override
	public String getMessage() {
		return this.message;
	}

	@Override
	public int getCode() {
		return this.code;
	}

}
