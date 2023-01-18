package com.libre.im.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.im.system.mapper.RoleMenuMapper;
import com.libre.im.system.pojo.entity.SysRoleMenu;
import com.libre.im.system.service.SysRoleMenuService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Libre
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

	@Override
	public List<SysRoleMenu> getListByRoleIds(Collection<Long> roleIds) {
		return super.list(Wrappers.<SysRoleMenu>lambdaQuery().in(SysRoleMenu::getRoleId, roleIds));
	}

	@Override
	public List<Long> getIdListByRoleIds(Collection<Long> roleIds) {
		List<SysRoleMenu> roleMenuList = this.getListByRoleIds(roleIds);
		if (CollectionUtils.isEmpty(roleMenuList)) {
			return Collections.emptyList();
		}
		return roleMenuList.stream().map(SysRoleMenu::getMenuId).distinct().collect(Collectors.toList());
	}

	@Override
	public boolean deleteByRoleId(Long roleId) {
		Wrapper<SysRoleMenu> wrapper = new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, roleId);
		return super.remove(wrapper);
	}

	@Override
	public List<SysRoleMenu> getListByMenuIds(List<Long> ids) {
		Wrapper<SysRoleMenu> wrapper = new LambdaQueryWrapper<SysRoleMenu>().in(SysRoleMenu::getMenuId, ids);
		return super.list(wrapper);
	}

}
