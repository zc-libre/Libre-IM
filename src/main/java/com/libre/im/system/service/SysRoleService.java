package com.libre.im.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.im.system.pojo.dto.RoleCriteria;
import com.libre.im.system.pojo.dto.RoleDTO;
import com.libre.im.system.pojo.entity.SysRole;
import com.libre.im.system.pojo.vo.RoleVO;

import java.util.Collection;
import java.util.List;

/**
 * @author zhao.cheng
 */
public interface SysRoleService extends IService<SysRole> {

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param roleCriteria 查询对象
	 * @return PageDTO
	 */
	PageDTO<RoleVO> findByPage(Page<SysRole> page, RoleCriteria roleCriteria);

	/**
	 * 根据用户 id 获取角色列表
	 * @param userId 用户id
	 * @return 角色列表
	 */
	List<SysRole> getListByUserId(Long userId);

	/**
	 * 更新角色菜单
	 * @param role 角色
	 * @param menuIds 菜单列表
	 * @return 是否成功
	 */
	boolean updateMenus(SysRole role, List<Long> menuIds);

	/**
	 * 如果没有使用，删除
	 * @param ids id 集合
	 * @return 是否成功
	 */
	boolean deleteIfUnusedByIds(Collection<Long> ids);

	/**
	 * 编辑
	 * @param roleDTO /
	 * @return /
	 */
	boolean edit(RoleDTO roleDTO);

}
