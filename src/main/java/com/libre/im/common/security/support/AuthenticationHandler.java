package com.libre.im.common.security.support;

import com.libre.im.common.security.dto.AuthUser;
import com.libre.toolkit.core.StringUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.PatternMatchUtils;

import java.util.Collection;

/**
 * 权限判断
 * <p>
 * url: <a href=
 * "https://stackoverflow.com/questions/41434231/use-spring-security-in-thymeleaf-escaped-expressions-in-javascript">...</a>
 *
 * @author l.cm
 */
@Service("sec")
public class AuthenticationHandler {

	/**
	 * 提供给页面输出当前用户
	 * @return {AuthUser}
	 */
	public AuthUser currentUser() {
		return SecurityUtil.getUser();
	}

	/**
	 * 判断是否超级管理员
	 * @return 是否管理员
	 */
	public boolean isAdmin() {
		AuthUser authUser = this.currentUser();
		if (authUser == null) {
			return false;
		}
		return SecurityUtil.isAdmin(authUser.getIsAdmin());
	}

	/**
	 * 已经授权的
	 * @return 是否授权
	 */
	public boolean isAuthenticated() {
		return this.currentUser() != null;
	}

	/**
	 * 判断按钮是否有xxx:xxx权限
	 * @param permissions 权限表达式
	 * @return {boolean}
	 */
	public boolean hasPermission(String... permissions) {
		if (StringUtil.isAnyBlank(permissions)) {
			return false;
		}
		Authentication authentication = SecurityUtil.getAuthentication();
		if (authentication == null) {
			return false;
		}
		AuthUser authUser = SecurityUtil.getUser(authentication);
		if (authUser == null) {
			return false;
		}
		// admin 有所有权限
		if (SecurityUtil.isAdmin(authUser.getIsAdmin())) {
			return true;
		}
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		return authorities.stream().map(GrantedAuthority::getAuthority).filter(StringUtil::isNotBlank)
				.anyMatch(x -> PatternMatchUtils.simpleMatch(permissions, x));
	}

}
