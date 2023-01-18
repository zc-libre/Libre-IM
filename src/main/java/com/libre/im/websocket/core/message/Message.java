package com.libre.im.websocket.core.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.libre.toolkit.time.DatePattern;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ZC
 * @date 2021/8/1 15:20
 */
@Data
public abstract class Message implements Serializable {

	protected Long id;

	protected Integer connectType;

	protected Integer messageBodyType;

	protected Long sendUserId;

	protected Integer status;

	protected Object body;

	protected Long acceptUserId;

	protected Long acceptGroupId;

	@JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
	@DateTimeFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
	protected LocalDateTime createTime;

}
