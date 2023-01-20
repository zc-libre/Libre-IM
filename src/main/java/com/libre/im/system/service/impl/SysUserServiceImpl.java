package com.libre.im.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.*;
import com.libre.boot.autoconfigure.SpringContext;
import com.libre.im.common.constant.CacheConstants;
import com.libre.im.security.pojo.dto.UserInfo;
import com.libre.im.system.constant.UserConstants;
import com.libre.im.system.mapper.SysUserMapper;
import com.libre.im.system.pojo.dto.UserCriteria;
import com.libre.im.system.pojo.dto.UserDTO;
import com.libre.im.system.pojo.entity.SysRole;
import com.libre.im.system.pojo.entity.SysUser;
import com.libre.im.system.pojo.entity.SysUserRole;
import com.libre.im.system.pojo.vo.UserVO;
import com.libre.im.system.service.SysRoleService;
import com.libre.im.system.service.SysUserRoleService;
import com.libre.im.system.service.SysUserService;
import com.libre.im.system.service.mapstruct.SysUserMapping;
import com.libre.mybatis.util.PageUtil;
import com.libre.toolkit.core.StringUtil;
import com.libre.toolkit.exception.LibreException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zhao.cheng
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = CacheConstants.SYS_USER_CACHE)
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

	private final SysRoleService roleService;

	private final SysUserRoleService userRoleService;

	@Override
	public PageDTO<UserVO> findByPage(Page<SysUser> page, UserCriteria userParam) {
		Page<SysUser> userPage = this.page(page, getQueryWrapper(userParam));
		List<SysUser> records = userPage.getRecords();
		if (CollectionUtils.isEmpty(records)) {
			return null;
		}

		Multimap<Long, Long> userIdRoleMap = LinkedHashMultimap.create();
		Set<Long> roleIdSet = Sets.newHashSet();

		Set<Long> userIdSet = records.stream().map(SysUser::getId).collect(Collectors.toSet());
		List<SysUserRole> userRoleList = userRoleService.getListByUserIds(userIdSet);

		if (CollectionUtils.isNotEmpty(userRoleList)) {
			for (SysUserRole sysUserRole : userRoleList) {
				userIdRoleMap.put(sysUserRole.getUserId(), sysUserRole.getRoleId());
				roleIdSet.add(sysUserRole.getRoleId());
			}
		}

		Map<Long, SysRole> roleMap = Maps.newHashMap();
		if (CollectionUtils.isNotEmpty(roleIdSet)) {
			Map<Long, SysRole> map = roleService.listByIds(roleIdSet).stream()
					.collect(Collectors.toMap(SysRole::getId, Function.identity()));
			roleMap.putAll(map);
		}

		List<UserVO> vos = Lists.newArrayList();
		for (SysUser sysUser : records) {
			UserVO userVO = SysUserMapping.INSTANCE.sourceToTarget(sysUser);

			// 角色
			Collection<Long> roleIds = userIdRoleMap.get(sysUser.getId());
			List<SysRole> roleList = Lists.newArrayList();
			if (CollectionUtils.isNotEmpty(roleIds)) {
				roleIds.forEach(roleId -> roleList.add(roleMap.get(roleId)));
			}
			userVO.setRoles(roleList);

			// 权限
			List<String> permissions = roleList.stream().map(SysRole::getRoleName).collect(Collectors.toList());
			userVO.setPermissions(permissions);

			vos.add(userVO);
		}
		return PageUtil.toPage(userPage, vos);
	}

	@Override
	@Cacheable(key = "#id")
	public SysUser findUserById(Long id) {
		return baseMapper.selectById(id);
	}


	@Override
	@Cacheable(key = "#username")
	public SysUser getByUsername(String username) {
		return this.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, username)
				.or()
				.eq(SysUser::getPhone, username));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true)
	public boolean updateByUsername(String username, SysUser sysUser) {
		LambdaUpdateWrapper<SysUser> wrapper = Wrappers.<SysUser>lambdaUpdate().eq(SysUser::getUsername, username);
		return this.update(sysUser, wrapper);
	}

	@Override
	@Cacheable(key = "'info-'+ #username")
	public UserInfo findUserInfoByUsername(String username) {
		SysUser sysUser = Optional.ofNullable(this.getByUsername(username))
				.orElseThrow(() -> new LibreException(String.format("用户不存在, username,: [%s]", username)));

		List<SysRole> roles = roleService.getListByUserId(sysUser.getId());
		List<String> permissions = Lists.newArrayList();
		if (CollectionUtils.isNotEmpty(roles)) {
			permissions = roles.stream().map(SysRole::getPermission).collect(Collectors.toList());
		}
		UserInfo userInfo = new UserInfo();
		userInfo.setUsername(username);
		userInfo.setAvatar(sysUser.getAvatar());
		userInfo.setPermissions(permissions);
		userInfo.setIsAdmin(sysUser.getIsAdmin());
		userInfo.setRoleList(roles);
		return userInfo;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true)
	public boolean createUser(UserDTO user) {
		String username = user.getUsername();

		if (StringUtil.isBlank(username)) {
			throw new LibreException("用户名不能为空");
		}
		SysUser dbUser = this.getByUsername(username);
		if (Objects.nonNull(dbUser)) {
			throw new LibreException("用户名已存在");
		}
		List<Long> roleIds = user.getRoleIds();
		if (CollectionUtils.isEmpty(roleIds)) {
			throw new LibreException("角色列表为空");
		}

		SysUserMapping userMapping = SysUserMapping.INSTANCE;
		SysUser sysUser = userMapping.convertToUser(user);
		sysUser.setLocked(UserConstants.USER_UNLOCK);
		this.save(sysUser);
		return userRoleService.saveByUserIdAndRoleIds(sysUser.getId(), roleIds);
	}

	@Override
	@CacheEvict(allEntries = true)
	@Transactional(rollbackFor = Exception.class)
	public boolean updateUser(UserDTO user) {
		String userName = user.getUsername();
		SysUserServiceImpl userService = SpringContext.getCurrentProxy();
		Optional.ofNullable(userService.getByUsername(userName)).orElseThrow(() -> new LibreException("用户不存在"));
		SysUserMapping mapping = SysUserMapping.INSTANCE;
		SysUser sysUser = mapping.convertToUser(user);
		List<Long> roleIds = user.getRoleIds();
		// 1. 更新用户
		super.updateById(sysUser);
		Long userId = sysUser.getId();
		// 2. 清除用户角色
		userRoleService.deleteByUserId(userId);
		// 4. 保存用户角色
		return userRoleService.saveByUserIdAndRoleIds(userId, roleIds);
	}

	@Override
	@CacheEvict(allEntries = true)
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteUserByIds(Set<Long> ids) {
		List<SysUserRole> userRoleList = userRoleService.getListByUserIds(ids);
		if (CollectionUtils.isNotEmpty(userRoleList)) {
			userRoleService.deleteByUserIds(ids);
		}
		this.removeBatchByIds(ids);
		return true;
	}

	@Override
	@CacheEvict(allEntries = true)
	@Transactional(rollbackFor = Exception.class)
	public boolean removeById(Serializable id) {
		return super.removeById(id);
	}

	private Wrapper<SysUser> getQueryWrapper(UserCriteria param) {
		String blurry = param.getBlurry();
		LambdaQueryWrapper<SysUser> wrapper = Wrappers.<SysUser>lambdaQuery().nested(param.isBlurryQuery(),
				q -> q.like(SysUser::getEmail, blurry).or().like(SysUser::getEmail, blurry).or()
						.like(SysUser::getNickName, blurry));
		if (param.haveTime()) {
			wrapper.between(SysUser::getGmtCreate, param.getStartTime(), param.getEndTime());
		}
		return wrapper;
	}

}
