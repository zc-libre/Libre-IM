package com.libre.im.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: Libre
 * @Date: 2022/12/31 5:26 PM
 */
@Getter
@AllArgsConstructor
public enum MenuType {

	DIRECTORY(0),

	MENU(1),

	BUTTON(2),;

	private final Integer type;

}
