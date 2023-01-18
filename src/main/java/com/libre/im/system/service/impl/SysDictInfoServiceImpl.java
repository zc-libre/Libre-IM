package com.libre.im.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.im.system.mapper.SysDictInfoMapper;
import com.libre.im.system.pojo.dto.DictInfoCriteria;
import com.libre.im.system.pojo.entity.SysDictInfo;
import com.libre.im.system.service.SysDictInfoService;
import com.libre.toolkit.core.StringUtil;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 字典详情表 服务实现类
 * </p>
 *
 * @author L.cm
 * @since 2020-07-19
 */
@Service
public class SysDictInfoServiceImpl extends ServiceImpl<SysDictInfoMapper, SysDictInfo> implements SysDictInfoService {

	@Override
	public Wrapper<SysDictInfo> getQueryWrapper(DictInfoCriteria query) {
		LambdaQueryWrapper<SysDictInfo> wrapper = new LambdaQueryWrapper<>();
		wrapper.like(StringUtil.isNotBlank(query.getLabel()), SysDictInfo::getLabel, query.getLabel());
		wrapper.eq(StringUtil.isNotBlank(query.getName()), SysDictInfo::getType, query.getName());
		return wrapper;
	}

	@Override
	public List<SysDictInfo> getListByDictNames(Collection<String> dictNames) {
		LambdaQueryWrapper<SysDictInfo> wrapper = new LambdaQueryWrapper<>();
		wrapper.in(SysDictInfo::getType, dictNames);
		return super.list(wrapper);
	}

}
