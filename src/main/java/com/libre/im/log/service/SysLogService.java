package com.libre.im.log.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.im.log.pojo.SysLog;
import com.libre.im.log.pojo.SysLogCriteria;

/**
 * @author: Libre
 * @Date: 2022/12/17 6:03 PM
 */
public interface SysLogService extends IService<SysLog> {

	PageDTO<SysLog> findPage(PageDTO<SysLog> page, SysLogCriteria criteria);

}
