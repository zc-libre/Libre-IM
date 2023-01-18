package com.libre.im.tookit.moudle.quartz.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.im.tookit.moudle.quartz.pojo.SysJob;
import com.libre.im.tookit.moudle.quartz.pojo.SysJobCriteria;

/**
 * <p>
 * Job Service
 * </p>
 *
 * @author yangkai.shen
 * @author L.cm
 */
public interface SysJobService extends IService<SysJob> {

	/**
	 * 查询定时任务列表
	 * @param page 分页对象
	 * @param criteria 查询对象
	 * @return 定时任务列表
	 */
	PageDTO<SysJob> findByPage(PageDTO<SysJob> page, SysJobCriteria criteria);

	/**
	 * 添加并启动定时任务
	 *
	 */
	void addJob(SysJob job);

	/**
	 * 删除定时任务
	 *
	 */
	void deleteJob(Long jobId);

	/**
	 * 暂停定时任务
	 *
	 */
	void pauseJob(SysJob sysJob);

	/**
	 * 恢复定时任务
	 *
	 */
	void resumeJob(SysJob sysJob);

	void updateJobStatus(SysJob sysJob);

	/**
	 * 重新配置定时任务
	 *
	 */
	void cronJob(SysJob sysJob);

}
