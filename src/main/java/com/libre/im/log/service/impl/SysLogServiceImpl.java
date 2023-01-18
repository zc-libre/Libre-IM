package com.libre.im.log.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.im.log.mapper.SysLogMapper;
import com.libre.im.log.service.SysLogService;
import com.libre.im.log.pojo.SysLog;
import com.libre.im.log.pojo.SysLogCriteria;
import com.libre.toolkit.core.StringUtil;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author: Libre
 * @Date: 2022/12/17 6:04 PM
 */
@Component
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

	public PageDTO<SysLog> findPage(PageDTO<SysLog> page, SysLogCriteria criteria) {
		return this.page(page, getQueryWrapper(criteria));
	}

	private Wrapper<SysLog> getQueryWrapper(SysLogCriteria query) {
		LambdaQueryWrapper<SysLog> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(SysLog::getSuccess, query.getSuccess());
		String blurry = query.getBlurry();
		// 模糊查询字段 username,description,address,requestIp,method,params
		wrapper.and(StringUtil.isNotBlank(blurry),
				w -> w.like(SysLog::getUsername, blurry).or().like(SysLog::getUsername, blurry).or()
						.like(SysLog::getDescription, blurry).or().like(SysLog::getAddress, blurry).or()
						.like(SysLog::getRequestIp, blurry).or().like(SysLog::getClassMethod, blurry).or()
						.like(SysLog::getParams, blurry));

		wrapper.eq(Objects.nonNull(query.getUserId()), SysLog::getUserId, query.getUserId());
		if (query.haveTime()) {
			wrapper.between(SysLog::getGmtCreate, query.getStartTime(), query.getEndTime());
		}
		wrapper.orderByDesc(SysLog::getGmtCreate);
		return wrapper;
	}

}
