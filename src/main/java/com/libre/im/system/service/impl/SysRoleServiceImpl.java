package com.libre.im.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.im.common.constant.CacheConstants;
import com.libre.im.system.constant.RoleConstants;
import com.libre.im.system.mapper.SysRoleMapper;
import com.libre.im.system.pojo.dto.RoleCriteria;
import com.libre.im.system.pojo.dto.RoleDTO;
import com.libre.im.system.pojo.entity.SysRole;
import com.libre.im.system.pojo.entity.SysRoleMenu;
import com.libre.im.system.pojo.entity.SysUserRole;
import com.libre.im.system.pojo.vo.RoleVO;
import com.libre.im.system.service.SysRoleMenuService;
import com.libre.im.system.service.SysRoleService;
import com.libre.im.system.service.SysUserRoleService;
import com.libre.im.system.service.mapstruct.SysRoleMapping;
import com.libre.mybatis.util.PageUtil;
import com.libre.toolkit.exception.LibreException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhao.cheng
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = CacheConstants.SYS_ROLE_CACHE)
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

	private final SysUserRoleService userRoleService;

	private final SysRoleMenuService roleMenuService;

	@Override
	public PageDTO<RoleVO> findByPage(Page<SysRole> page, RoleCriteria roleCriteria) {
		Page<SysRole> rolePage = this.page(page, getQueryWrapper(roleCriteria));
		List<SysRole> records = rolePage.getRecords();
		SysRoleMapping mapping = SysRoleMapping.INSTANCE;
		List<RoleVO> vos = mapping.convertToRoleList(records);
		return PageUtil.toPage(page, vos);
	}

	@Override
	@Cacheable(key = "#userId")
	public List<SysRole> getListByUserId(Long userId) {
		List<SysUserRole> userRoleList = userRoleService.getListByUserId(userId);
		if (CollectionUtils.isEmpty(userRoleList)) {
			return Collections.emptyList();
		}
		Set<Long> roleIds = userRoleList.stream().map(SysUserRole::getRoleId).collect(Collectors.toSet());
		return this.list(Wrappers.<SysRole>lambdaQuery().in(SysRole::getId, roleIds).eq(SysRole::getStatus,
				RoleConstants.ROLE_ENABLE));
	}

	@Override
	@Transactional(rollbackFor = Throwable.class)
	@CacheEvict(allEntries = true)
	public boolean updateMenus(SysRole role, List<Long> menuIds) {
		Long roleId = role.getId();
		// 1. 清空角色菜单
		roleMenuService.deleteByRoleId(roleId);
		// 2. 批量保存
		if (CollectionUtils.isNotEmpty(menuIds)) {
			List<SysRoleMenu> entityList = new ArrayList<>(menuIds.size());
			for (Long menuId : menuIds) {
				SysRoleMenu roleMenu = new SysRoleMenu();
				roleMenu.setRoleId(roleId);
				roleMenu.setMenuId(menuId);
				entityList.add(roleMenu);
			}
			roleMenuService.saveBatch(entityList);
		}
		return true;
	}

	@Override
	@Transactional(rollbackFor = Throwable.class)
	@CacheEvict(allEntries = true)
	public boolean deleteIfUnusedByIds(Collection<Long> ids) {
		List<SysUserRole> userRoleList = userRoleService.getListByRoleIds(ids);
		if (userRoleList != null && !userRoleList.isEmpty()) {
			throw new LibreException("存在用户角色关系");
		}
		List<SysRoleMenu> roleMenuList = roleMenuService.getListByRoleIds(ids);
		if (roleMenuList != null && !roleMenuList.isEmpty()) {
			throw new LibreException("存在菜单角色关系");
		}
		return super.removeByIds(ids);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true)
	public boolean edit(RoleDTO roleDTO) {
		SysRoleMapping mapping = SysRoleMapping.INSTANCE;
		SysRole sysRole = mapping.convertToRole(roleDTO);
		return super.updateById(sysRole);
	}

	private Wrapper<SysRole> getQueryWrapper(RoleCriteria roleCriteria) {
		String blurry = roleCriteria.getBlurry();
		LambdaQueryWrapper<SysRole> wrapper = Wrappers.<SysRole>lambdaQuery().nested(roleCriteria.isBlurryQuery(),
				q -> q.like(SysRole::getRoleName, blurry));
		if (roleCriteria.haveTime()) {
			wrapper.between(SysRole::getGmtCreate, roleCriteria.getStartTime(), roleCriteria.getEndTime());
		}
		return wrapper;
	}

}
