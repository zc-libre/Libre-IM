package com.libre.im.web.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.im.web.constant.CacheKey;
import com.libre.im.web.mapper.SysUserMapper;
import com.libre.im.web.pojo.LibreUser;
import com.libre.im.web.service.SysUserService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ZC
 * @date 2021/8/14 21:54
 */
@Service
@CacheConfig(cacheNames = CacheKey.USER_CACHE_KEY)
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, LibreUser> implements SysUserService {

	@Override
	@Cacheable(key = "#username")
	public LibreUser findByUsername(String username) {
		return this.getOne(Wrappers.<LibreUser>lambdaQuery().eq(LibreUser::getUsername, username));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true)
	public boolean updateByUsername(String username, LibreUser sysUser) {
		return this.update(sysUser, Wrappers.<LibreUser>lambdaUpdate().eq(LibreUser::getUsername, username));
	}

}
