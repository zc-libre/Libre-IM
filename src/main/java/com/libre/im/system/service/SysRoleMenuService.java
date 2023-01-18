package com.libre.im.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.im.system.pojo.entity.SysRoleMenu;

import java.util.Collection;
import java.util.List;

/**
 * @author zhao.cheng
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

	/**
	 * 角色菜单列表
	 * @param roleIds 角色id集合
	 * @return 角色菜单
	 */
	List<SysRoleMenu> getListByRoleIds(Collection<Long> roleIds);

	/**
	 * 角色菜单 id 列表
	 * @param roleIds 角色id集合
	 * @return 角色菜单
	 */
	List<Long> getIdListByRoleIds(Collection<Long> roleIds);

	/**
	 * 清空角色菜单
	 * @param roleId 角色Id
	 */
	boolean deleteByRoleId(Long roleId);

	List<SysRoleMenu> getListByMenuIds(List<Long> ids);

}
