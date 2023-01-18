package com.libre.im.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.im.system.mapper.SysDictMapper;
import com.libre.im.system.pojo.dto.DictCriteria;
import com.libre.im.system.pojo.entity.SysDict;
import com.libre.im.system.pojo.entity.SysDictInfo;
import com.libre.im.system.service.SysDictInfoService;
import com.libre.im.system.service.SysDictService;
import com.libre.toolkit.core.StringUtil;
import com.libre.toolkit.exception.LibreException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author L.cm
 * @since 2020-07-19
 */
@Service
@RequiredArgsConstructor
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {

	private final SysDictInfoService dictInfoService;

	@Override
	public Wrapper<SysDict> getQueryWrapper(DictCriteria query) {
		String queryBlurry = query.getBlurry();
		LambdaQueryWrapper<SysDict> wrapper = new LambdaQueryWrapper<>();
		// name,description
		wrapper.and(StringUtil.isNotBlank(queryBlurry), w -> w.like(SysDict::getName, queryBlurry).or()
				.like(SysDict::getDescription, queryBlurry).or().like(SysDict::getRemark, queryBlurry));
		return wrapper;
	}

	@Override
	public boolean deleteIfUnusedByIds(Collection<Long> ids) {
		List<SysDict> sysDictList = super.listByIds(ids);
		if (sysDictList == null || sysDictList.isEmpty()) {
			return false;
		}
		Set<String> dictNameSet = sysDictList.stream().map(SysDict::getName).collect(Collectors.toSet());
		List<SysDictInfo> dictInfoList = dictInfoService.getListByDictNames(dictNameSet);
		if (dictInfoList != null && !dictInfoList.isEmpty()) {
			throw new LibreException("存在字典详情关系");
		}
		return super.removeByIds(ids);
	}

}
