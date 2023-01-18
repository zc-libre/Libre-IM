package com.libre.im.tookit.moudle.quartz.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.libre.im.tookit.moudle.quartz.pojo.SysJob;
import com.libre.im.tookit.moudle.quartz.pojo.SysJobCriteria;
import com.libre.im.tookit.moudle.quartz.service.SysJobService;
import com.libre.toolkit.result.R;
import com.libre.toolkit.validation.UpdateGroup;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * Job Controller
 * </p>
 *
 * @author yangkai.shen
 * @author L.cm
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/sys/job")
@RequiredArgsConstructor
@Tag(name = "系统：任务管理")
public class SysJobController {

	private final SysJobService jobService;

	/**
	 * 任务列表查询
	 */
	@Operation(summary = "任务列表查询")
	@GetMapping("page")
	public R<IPage<SysJob>> jobList(PageDTO<SysJob> page, SysJobCriteria query) {
		IPage<SysJob> jobPage = jobService.findByPage(page, query);
		return R.data(jobPage);
	}

	/**
	 * 保存定时任务
	 */
	@Operation(summary = "保存定时任务")
	@PostMapping
	public void addJob(@Validated @RequestBody SysJob form) {
		jobService.addJob(form);
	}

	/**
	 * 暂停定时任务
	 */
	@Operation(summary = "暂停定时任务")
	@PutMapping("/pause")
	public void pauseJob(@Validated(UpdateGroup.class) @RequestBody SysJob form) {
		jobService.pauseJob(form);
	}

	@Operation(summary = "恢复定时任务")
	@PutMapping("/update/{id}")
	public void update(@PathVariable Long id) {
		SysJob sysJob = jobService.getById(id);
		jobService.updateJobStatus(sysJob);
	}

	/**
	 * 恢复定时任务
	 */
	@Operation(summary = "恢复定时任务")
	@PutMapping("/resume")
	public void resumeJob(@Validated(UpdateGroup.class) @RequestBody SysJob form) {
		jobService.resumeJob(form);
	}

	/**
	 * 修改定时任务，定时时间
	 */
	@Operation(summary = "修改定时任务")
	@PutMapping("/cron")
	public void cronJob(@Validated @RequestBody SysJob form) {
		jobService.cronJob(form);
	}

	/**
	 * 删除定时任务
	 */
	@Operation(summary = "删除定时任务")
	@DeleteMapping("{id}")
	public void deleteJob(@PathVariable Long id) {
		jobService.deleteJob(id);
	}

}
