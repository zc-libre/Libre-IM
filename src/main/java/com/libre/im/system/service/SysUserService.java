package com.libre.im.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.im.security.pojo.dto.UserInfo;
import com.libre.im.system.pojo.dto.UserCriteria;
import com.libre.im.system.pojo.dto.UserDTO;
import com.libre.im.system.pojo.entity.SysUser;
import com.libre.im.system.pojo.vo.UserVO;

import java.util.Set;

/**
 * @author zhao.cheng
 */
public interface SysUserService extends IService<SysUser> {

	/**
	 * 分页查询
	 * @param page page
	 * @param userParam userParam
	 * @return IPage<UserVO>
	 */
	PageDTO<UserVO> findByPage(Page<SysUser> page, UserCriteria userParam);

	/**
	 * 通过id查找用户
	 * @param id /
	 * @return /
	 */
	SysUser findUserById(Long id);

	/**
	 * 根据登录名查找用户
	 * @param username 登录名
	 * @return 用户
	 */
	SysUser getByUsername(String username);

	/**
	 * 按照用户账号更新
	 * @param sysUser 用户
	 */
	boolean updateByUsername(String username, SysUser sysUser);

	UserInfo findUserInfoByUsername(String username);

	boolean createUser(UserDTO user);

	boolean updateUser(UserDTO user);

	boolean deleteUserByIds(Set<Long> ids);

}
