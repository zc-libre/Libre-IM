package com.libre.im.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.im.system.mapper.UserRoleMapper;
import com.libre.im.system.pojo.entity.SysUserRole;
import com.libre.im.system.service.SysUserRoleService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Libre
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<UserRoleMapper, SysUserRole> implements SysUserRoleService {

	@Override
	public List<SysUserRole> getListByUserId(Long userId) {
		return this.list(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, userId));
	}

	@Override
	public List<SysUserRole> getListByUserIds(Collection<Long> userIds) {
		return this.list(Wrappers.<SysUserRole>lambdaQuery().in(SysUserRole::getUserId, userIds));
	}

	@Override
	public List<SysUserRole> getListByRoleIds(Collection<Long> roleIds) {
		return super.list(Wrappers.<SysUserRole>lambdaQuery().in(SysUserRole::getRoleId, roleIds));
	}

	@Override
	public boolean saveByUserIdAndRoleIds(Long userId, List<Long> roleIds) {
		List<SysUserRole> userRoleList = roleIds.stream().map(roleId -> {
			SysUserRole userRole = new SysUserRole();
			userRole.setUserId(userId);
			userRole.setRoleId(roleId);
			return userRole;
		}).distinct().collect(Collectors.toList());
		return super.saveBatch(userRoleList);
	}

	@Override
	public boolean deleteByUserId(Long userId) {
		return super.remove(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, userId));
	}

	@Override
	public boolean deleteByUserIds(Set<Long> userIds) {
		return super.remove(Wrappers.<SysUserRole>lambdaQuery().in(SysUserRole::getUserId, userIds));
	}

}
