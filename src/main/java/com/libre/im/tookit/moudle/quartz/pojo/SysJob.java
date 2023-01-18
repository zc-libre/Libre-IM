package com.libre.im.tookit.moudle.quartz.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.libre.im.common.pojo.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author: Libre
 * @Date: 2022/12/30 8:44 PM
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_job")
public class SysJob extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 42L;

	public static final String JOB_KEY = "JOB_KEY";

	/**
	 * 定时任务名称
	 */
	private String jobName;

	/**
	 * 任务状态
	 */
	private Integer jobStatus;

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

}
