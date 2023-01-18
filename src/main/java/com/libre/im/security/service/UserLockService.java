package com.libre.im.security.service;

import com.libre.im.common.security.dto.AuthUser;

public interface UserLockService {

	/**
	 * 锁定用户
	 * @param authUser AuthUser
	 * @return {boolean}
	 */
	boolean updateLockUser(AuthUser authUser);

}
