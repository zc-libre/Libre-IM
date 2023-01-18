package com.libre.im.tookit.moudle.quartz;

import com.libre.im.tookit.moudle.quartz.annotation.ScheduleTask;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: Libre
 * @Date: 2022/12/30 10:57 PM
 */
@Slf4j
@ScheduleTask
public class TestJob {

	public void execute() {
		log.info("定时任务开始执行.........");
	}

}
