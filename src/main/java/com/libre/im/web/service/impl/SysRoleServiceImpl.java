package com.libre.im.web.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.core.toolkit.CollectionUtil;
import com.libre.im.security.pojo.SysRole;
import com.libre.im.security.pojo.SysUserRole;
import com.libre.im.web.service.SysRoleService;
import com.libre.im.web.mapper.SysRoleMapper;
import com.libre.im.web.service.SysUserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: Libre
 * @Date: 2022/5/29 11:45 PM
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

	private final SysUserRoleService userRoleService;

	@Override
	public List<SysRole> getListByUserId(Long userId) {
		List<SysUserRole> userRoleList = userRoleService
				.list(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, userId));

		if (CollectionUtil.isEmpty(userRoleList)) {
			return Collections.emptyList();
		}

		Set<Long> roleIds = userRoleList.stream().map(SysUserRole::getRoleId).collect(Collectors.toSet());
		return this.listByIds(roleIds);
	}

}
