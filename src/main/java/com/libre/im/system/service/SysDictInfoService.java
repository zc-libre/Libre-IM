package com.libre.im.system.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.im.system.pojo.dto.DictInfoCriteria;
import com.libre.im.system.pojo.entity.SysDictInfo;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 字典详情表 服务类
 * </p>
 *
 * @author L.cm
 * @since 2020-07-19
 */
public interface SysDictInfoService extends IService<SysDictInfo> {

	/**
	 * 根据 query 组装查询条件
	 * @param query DictInfoCriteria
	 * @return Wrapper
	 */
	Wrapper<SysDictInfo> getQueryWrapper(DictInfoCriteria query);

	/**
	 * 获取字典详情集合
	 * @param dictNameSet 字典名集合
	 * @return 字典详情集合
	 */
	List<SysDictInfo> getListByDictNames(Collection<String> dictNameSet);

}
