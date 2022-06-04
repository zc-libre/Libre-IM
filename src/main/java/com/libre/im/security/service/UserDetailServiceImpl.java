package com.libre.im.security.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.libre.core.toolkit.CollectionUtil;
import com.libre.core.toolkit.StringUtil;
import com.libre.im.security.constant.SecurityConstants;
import com.libre.im.security.pojo.RoleInfo;
import com.libre.im.security.pojo.SysRole;
import com.libre.im.security.pojo.dto.AuthUser;
import com.libre.im.security.pojo.dto.AuthUserDTO;
import com.libre.im.web.pojo.LibreUser;
import com.libre.im.web.service.SysUserService;
import com.libre.im.web.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * @author Libre
 * @date 2021/7/12 14:09
 */
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService, UserDetailsPasswordService, UserLockService {

	private final SysUserService userService;

	private final SysRoleService sysRoleService;

	@Override
	public AuthUser loadUserByUsername(String username) throws UsernameNotFoundException {
		if (StringUtil.isBlank(username)) {
			throw new UsernameNotFoundException("userName is blank!");
		}
		LibreUser user = userService.findByUsername(username);
		if (Objects.isNull(user)) {
			throw new UsernameNotFoundException("User is not found!");
		}
		Long userId = user.getId();

		List<SysRole> roleList = sysRoleService.getListByUserId(userId);
		Set<String> dbAuthSet = Sets.newHashSet();
		List<RoleInfo> roleInfoList = Lists.newArrayList();
		if (CollectionUtil.isNotEmpty(roleList)) {
			// 获取角色
			loadRoleAuthorities(roleList, dbAuthSet);
		}
		String password = user.getPassword();
		Integer enabled = user.getEnabled();
		Integer locked = user.getLocked();

		Collection<? extends GrantedAuthority> authorities = AuthorityUtils
				.createAuthorityList(dbAuthSet.toArray(new String[0]));

		boolean enable = ObjectUtils.nullSafeEquals(enabled, SecurityConstants.ENABLED_YES);
		boolean accountNonLocked = ObjectUtils.nullSafeEquals(locked, SecurityConstants.LOCKED_YES);

		AuthUser jwtUser = new AuthUser(username, password, enable,
				accountNonLocked, authorities);
		jwtUser.setUserId(user.getId());
		jwtUser.setNickName(user.getNikeName());
		jwtUser.setGender(user.getGender());
		jwtUser.setPhone(user.getPhone());
		jwtUser.setAvatar(user.getAvatar());

		if (CollectionUtil.isNotEmpty(roleInfoList)) {
			jwtUser.setRoleList(roleInfoList);
		}

		return jwtUser;
	}

	@Override
	public UserDetails updatePassword(UserDetails user, String newPassword) {
		AuthUser authUser = (AuthUser) user;
		LibreUser sysUser = new LibreUser();
		sysUser.setId(authUser.getUserId());
		sysUser.setPassword(newPassword);
		userService.updateById(sysUser);
		return AuthUser.formUser(authUser, newPassword);
	}

	@Override
	public boolean updateLockUser(AuthUserDTO authUser) {
		Assert.notNull(authUser.getUsername(), "username must not be null");
		LibreUser sysUser = new LibreUser();
		sysUser.setLocked(SecurityConstants.LOCKED_YES);
		return userService.updateByUsername(authUser.getUsername(), sysUser);
	}

	private void loadRoleAuthorities(List<SysRole> roleList, Set<String> dbAuthsSet) {
		roleList.stream().map(SysRole::getRoleName).filter(StringUtil::isNotBlank).forEach(dbAuthsSet::add);
	}

}
