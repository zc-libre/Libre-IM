package com.libre.im.tookit.moudle.quartz.pojo;

import lombok.Data;

/**
 * @author: Libre
 * @Date: 2022/12/30 11:03 PM
 */
@Data
public class SysJobVO {

	public static final String JOB_KEY = "JOB_KEY";

	/**
	 * 定时任务名称
	 */
	private String jobName;

	/**
	 * 定时任务组
	 */
	private String jobGroup;

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
	 * 触发器名称
	 */
	private String triggerName;

	/**
	 * 触发器组
	 */
	private String triggerGroup;

	/**
	 * 重复间隔
	 */
	private Long repeatInterval;

	/**
	 * 触发次数
	 */
	private Long timesTriggered;

	/**
	 * cron 表达式
	 */
	private String cronExpression;

	/**
	 * 时区
	 */
	private String timeZoneId;

	/**
	 * 定时任务状态
	 */
	private String triggerState;

}
