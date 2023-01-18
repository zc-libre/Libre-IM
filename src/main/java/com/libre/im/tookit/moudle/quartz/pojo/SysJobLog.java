package com.libre.im.tookit.moudle.quartz.pojo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: Libre
 * @Date: 2022/12/30 10:35 PM
 */
@Data
public class SysJobLog {

	private Long id;

	/**
	 * 任务名称
	 */
	private String jobName;

	/**
	 * 定时任务beanName
	 */
	private String beanName;

	/**
	 * 方法名
	 */
	private String methodName;

	/**
	 * 参数
	 */
	private String params;

	/**
	 * cron 表达式
	 */
	private String cronExpression;

	/**
	 * 执行时间
	 */
	private Long executeTime;

	/**
	 * 是否成功
	 */
	private Integer success;

	/**
	 * 异常信息
	 */
	private String exceptionDetail;

	/**
	 * 最后一次执行时间
	 */
	private LocalDateTime lastExecuteTime;

	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;

}
