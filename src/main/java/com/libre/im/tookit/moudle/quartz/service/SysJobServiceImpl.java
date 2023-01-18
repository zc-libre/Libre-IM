package com.libre.im.tookit.moudle.quartz.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.boot.autoconfigure.SpringContext;
import com.libre.im.tookit.moudle.quartz.constant.JobStatus;
import com.libre.im.tookit.moudle.quartz.mapper.SysJobMapper;
import com.libre.im.tookit.moudle.quartz.pojo.SysJob;
import com.libre.im.tookit.moudle.quartz.pojo.SysJobCriteria;
import com.libre.im.tookit.moudle.quartz.support.ScheduleJob;
import com.libre.toolkit.exception.LibreException;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author: Libre
 * @Date: 2022/12/30 9:18 PM
 */
@Service
@RequiredArgsConstructor
public class SysJobServiceImpl extends ServiceImpl<SysJobMapper, SysJob> implements SysJobService {

	private final Scheduler scheduler;

	private static final String JOB_NAME = "TASK_";

	@Override
	public PageDTO<SysJob> findByPage(PageDTO<SysJob> page, SysJobCriteria criteria) {
		return this.page(page, getQueryWrapper(criteria));
	}

	private LambdaQueryWrapper<SysJob> getQueryWrapper(SysJobCriteria criteria) {
		return Wrappers.<SysJob>lambdaQuery().eq(Objects.nonNull(criteria.getJobStatus()), SysJob::getJobStatus,
				criteria);
	}

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public void addJob(SysJob job) {
		checkBeanExist(job);
		try {
			this.save(job);
			// 构建job信息
			JobDetail jobDetail = JobBuilder.newJob(ScheduleJob.class).withIdentity(JOB_NAME + job.getId()).build();

			// 通过触发器名和cron 表达式创建 Trigger
			Trigger cronTrigger = newTrigger().withIdentity(JOB_NAME + job.getId()).startNow()
					.withSchedule(CronScheduleBuilder.cronSchedule(job.getCronExpression())).build();

			cronTrigger.getJobDataMap().put(SysJob.JOB_KEY, job);

			// 重置启动时间
			((CronTriggerImpl) cronTrigger).setStartTime(new Date());

			// 执行定时任务
			scheduler.scheduleJob(jobDetail, cronTrigger);

			// 暂停任务
			if (JobStatus.PAUSE.getType().equals(job.getJobStatus())) {
				pauseJob(job);
			}

		}
		catch (Exception e) {
			log.error("创建定时任务失败", e);
			throw new LibreException("创建定时任务失败");
		}
	}

	private static void checkBeanExist(SysJob job) {
		String beanName = job.getBeanName();
		Object bean = SpringContext.getBean(beanName);
		if (Objects.isNull(bean)) {
			throw new LibreException("bean不存在");
		}
	}

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public void deleteJob(Long jobId) {
		SysJob sysJob = baseMapper.selectById(jobId);
		try {
			scheduler.pauseTrigger(TriggerKey.triggerKey(JOB_NAME + sysJob.getId()));
			scheduler.unscheduleJob(TriggerKey.triggerKey(JOB_NAME + sysJob.getId()));
			scheduler.deleteJob(JobKey.jobKey(JOB_NAME + sysJob.getId()));
			this.removeById(jobId);
		}
		catch (SchedulerException e) {
			log.error(e.getMessage(), e);
			throw new LibreException("任务删除失败");
		}
	}

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public void pauseJob(SysJob sysJob) {
		try {
			scheduler.pauseJob(JobKey.jobKey(JOB_NAME + sysJob.getId()));
		}
		catch (SchedulerException e) {
			log.error(e.getMessage(), e);
			throw new LibreException("任务暂停失败");
		}
	}

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public void updateJobStatus(SysJob sysJob) {
		if (JobStatus.EXECUTE.getType().equals(sysJob.getJobStatus())) {
			this.pauseJob(sysJob);
			sysJob.setJobStatus(JobStatus.PAUSE.getType());
		}
		else {
			this.resumeJob(sysJob);
			sysJob.setJobStatus(JobStatus.EXECUTE.getType());
		}
		this.updateById(sysJob);
	}

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public void resumeJob(SysJob sysJob) {
		try {
			TriggerKey triggerKey = TriggerKey.triggerKey(JOB_NAME + sysJob.getId());
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			// 如果不存在则创建一个定时任务
			if (trigger == null) {
				addJob(sysJob);
			}
			JobKey jobKey = JobKey.jobKey(JOB_NAME + sysJob.getId());
			scheduler.resumeJob(jobKey);
		}
		catch (SchedulerException e) {
			log.error(e.getMessage(), e);
			throw new LibreException("任务恢复失败");
		}
	}

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public void cronJob(SysJob sysJob) {
		checkBeanExist(sysJob);
		try {
			this.updateById(sysJob);
			TriggerKey triggerKey = TriggerKey.triggerKey(JOB_NAME + sysJob.getId());
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			// 如果不存在则创建一个定时任务
			if (trigger == null) {
				addJob(sysJob);
				trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			}
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(sysJob.getCronExpression());
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
			// 重置启动时间
			((CronTriggerImpl) trigger).setStartTime(new Date());
			trigger.getJobDataMap().put(SysJob.JOB_KEY, sysJob);

			scheduler.rescheduleJob(triggerKey, trigger);
			// 暂停任务
			if (JobStatus.PAUSE.getType().equals(sysJob.getJobStatus())) {
				pauseJob(sysJob);
			}
		}
		catch (SchedulerException e) {
			log.error(e.getMessage(), e);
			throw new LibreException("任务重置失败");
		}
	}

}
