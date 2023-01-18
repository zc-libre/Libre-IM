package com.libre.im.security.auth;

import com.libre.im.common.security.dto.AuthUser;
import com.libre.im.security.config.LibreSecurityProperties;
import com.libre.im.security.service.UserLockService;
import com.libre.im.security.service.UserDetailServiceImpl;
import com.libre.toolkit.exception.LibreException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.Assert;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义授权处理，添加验证码
 *
 * @author L.cm
 */
public class SecAuthenticationProvider extends DaoAuthenticationProvider {

	@Getter
	@Setter
	private LibreSecurityProperties micaSecurityProperties;

	@Getter
	@Setter
	private CacheManager cacheManager;

	private Cache passwordRetryCache;

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		if (auth.isAuthenticated()) {
			return auth;
		}

		UsernamePasswordAuthenticationToken token;
		try {
			token = decodeRsaPassword(auth);
		}
		catch (Throwable e) {
			throw new LibreException("密码被篡改，解密失败");
		}
		return super.authenticate(token);
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		// 添加用户锁定的功能，用户尝试登录密码错误太多次锁定账号
		String username = userDetails.getUsername();
		// retry count + 1
		AtomicInteger retryCount = passwordRetryCache.get(username, AtomicInteger.class);
		if (retryCount == null) {
			retryCount = new AtomicInteger(0);
			passwordRetryCache.put(username, retryCount);
		}
		int retryLimit = micaSecurityProperties.getLogin().getRetryLimit();
		if (retryCount.incrementAndGet() > retryLimit) {
			// if retry count > retryLimit
			logger.warn("username: " + username + " tried to login more than " + retryLimit + " times in period");
			UserLockService userLockService = this.getUserLockService();
			userLockService.updateLockUser((AuthUser) userDetails);
			throw new LibreException("登录错误" + retryCount + "次，账号已锁定");
		}
		else {
			passwordRetryCache.put(username, retryCount);
		}
		super.additionalAuthenticationChecks(userDetails, authentication);
		// clear retry data
		passwordRetryCache.evict(username);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	@Override
	protected void doAfterPropertiesSet() {
		super.doAfterPropertiesSet();
		Assert.notNull(micaSecurityProperties, "dreamProperties is null");
		Assert.notNull(cacheManager, "cacheManager is null");
		String retryLimitCacheName = micaSecurityProperties.getLogin().getRetryLimitCacheName();
		this.passwordRetryCache = cacheManager.getCache(retryLimitCacheName);
		Assert.notNull(this.passwordRetryCache,
				"retryLimitCache retryLimitCacheName: " + retryLimitCacheName + " is not config.");
	}

	private UserDetailServiceImpl getUserLockService() {
		UserDetailsService userDetailsService = super.getUserDetailsService();
		return (UserDetailServiceImpl) userDetailsService;
	}

	private UsernamePasswordAuthenticationToken decodeRsaPassword(Authentication auth) {
		Object credentials = auth.getCredentials();
		UsernamePasswordAuthenticationToken newToken = new UsernamePasswordAuthenticationToken(auth.getPrincipal(),
				credentials);
		newToken.setDetails(auth.getDetails());
		return newToken;
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		// 不做处理，使用 security 内置的 i18n 配置
	}

}
