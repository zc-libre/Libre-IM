package com.libre.im.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.im.system.pojo.entity.SysUserRole;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author zhao.cheng
 */
public interface SysUserRoleService extends IService<SysUserRole> {

	/**
	 * 根据用户id获取用户角色关系列表
	 * @param userId 用户id
	 * @return 用户角色关系列表
	 */
	List<SysUserRole> getListByUserId(Long userId);

	/**
	 * 根据用户id获取用户角色关系列表
	 * @param userIds 用户id
	 * @return 用户角色关系列表
	 */
	List<SysUserRole> getListByUserIds(Collection<Long> userIds);

	/**
	 * 根据角色id列表获取用户角色关系
	 * @param roleIds 角色id
	 * @return 用户角色关系列表
	 */
	List<SysUserRole> getListByRoleIds(Collection<Long> roleIds);

	/**
	 * 保存用户角色
	 * @param userId 用户id
	 * @param roleIds 角色id列表
	 * @return 是否成功
	 */
	boolean saveByUserIdAndRoleIds(Long userId, List<Long> roleIds);

	/**
	 * 删除用户角色
	 * @param userId 用户id
	 * @return 是否成功
	 */
	boolean deleteByUserId(Long userId);

	/**
	 * 批量删除用户角色
	 * @param userIds 用户id
	 * @return 是否成功
	 */
	boolean deleteByUserIds(Set<Long> userIds);

}
