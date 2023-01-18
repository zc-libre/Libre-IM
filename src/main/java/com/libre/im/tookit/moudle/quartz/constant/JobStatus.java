package com.libre.im.tookit.moudle.quartz.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: Libre
 * @Date: 2023/1/8 3:46 AM
 */
@Getter
@AllArgsConstructor
public enum JobStatus {

	EXECUTE(0),

	PAUSE(1),;

	private final Integer type;

}
