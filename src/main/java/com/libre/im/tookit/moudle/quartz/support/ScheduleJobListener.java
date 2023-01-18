package com.libre.im.tookit.moudle.quartz.support;

import com.google.common.base.Throwables;
import com.libre.im.tookit.common.SysJobConstant;
import com.libre.im.tookit.moudle.quartz.mapper.SysJobLogMapper;
import com.libre.im.tookit.moudle.quartz.pojo.SysJob;
import com.libre.im.tookit.moudle.quartz.pojo.SysJobLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.EverythingMatcher;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author: Libre
 * @Date: 2022/12/30 10:26 PM
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduleJobListener implements JobListener {

	private final SchedulerFactoryBean schedulerFactory;

	private final SysJobLogMapper sysJobLogMapper;

	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {

	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {

	}

	@Override
	public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException e) {
		SysJob sysJob = (SysJob) jobExecutionContext.getMergedJobDataMap().get(SysJob.JOB_KEY);
		SysJobLog sysJobLog = new SysJobLog();
		sysJobLog.setJobName(sysJob.getJobName());
		sysJobLog.setBeanName(sysJobLog.getBeanName());
		sysJobLog.setMethodName(sysJobLog.getMethodName());
		sysJobLog.setParams(sysJob.getParams());
		sysJobLog.setCronExpression(sysJobLog.getCronExpression());
		sysJobLog.setCreateTime(LocalDateTime.now());
		sysJobLog.setLastExecuteTime(LocalDateTime.now());
		sysJobLog.setSuccess(SysJobConstant.JOB_SUCCESS);
		Optional.ofNullable(e).ifPresent(exception -> {
			sysJobLog.setExceptionDetail(Throwables.getStackTraceAsString(exception));
			sysJobLog.setSuccess(SysJobConstant.JOB_FAILED);
		});
		sysJobLogMapper.insert(sysJobLog);
	}

	// 注册 JobListener 到 Quartz 中
	@PostConstruct
	public void init() throws SchedulerException {
		Scheduler scheduler = schedulerFactory.getScheduler();
		scheduler.getListenerManager().addJobListener(this, EverythingMatcher.allJobs());
	}

}
