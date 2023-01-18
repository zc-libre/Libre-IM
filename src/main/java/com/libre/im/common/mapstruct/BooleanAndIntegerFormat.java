package com.libre.im.common.mapstruct;

import com.libre.im.common.constant.LibreConstants;

/**
 * @author: Libre
 * @Date: 2023/1/8 1:42 AM
 */
public class BooleanAndIntegerFormat {

	public Integer toInteger(Boolean status) {
		if (Boolean.FALSE.equals(status)) {
			return LibreConstants.DISABLE;
		}
		else if (Boolean.TRUE.equals(status)) {
			return LibreConstants.ENABLE;
		}
		return null;
	}

	public Boolean toBoolean(Integer status) {
		if (LibreConstants.ENABLE.equals(status)) {
			return Boolean.TRUE;
		}
		else if (LibreConstants.DISABLE.equals(status)) {
			return Boolean.FALSE;
		}
		return null;
	}

}
