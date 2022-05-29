package com.libre.im.admin.security.service;

import com.libre.im.admin.security.service.dto.AuthUserDTO;

public interface UserLockService {

	/**
	 * 锁定用户
	 *
	 * @param authUser AuthUser
	 * @return {boolean}
	 */
	boolean updateLockUser(AuthUserDTO authUser);

}
