package com.libre.im.security.auth;

import com.libre.boot.toolkit.RequestUtils;
import com.libre.im.common.security.dto.AuthUser;
import com.libre.im.common.security.dto.JwtUser;
import com.libre.im.common.security.support.SecurityUtil;
import com.libre.im.log.support.SysLogUtil;
import com.libre.im.security.config.LibreSecurityProperties;
import com.libre.im.log.support.SysLogConstant;
import com.libre.im.log.support.SysLogEvent;
import com.libre.im.log.support.SysLogType;
import com.libre.im.security.jwt.JwtTokenService;
import com.libre.im.security.jwt.JwtTokenStore;
import com.libre.toolkit.exception.LibreException;
import com.libre.toolkit.result.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.Date;

/**
 * 成功、失败的处理器
 *
 * @author L.cm
 */
@Slf4j
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class SecAuthHandler extends AccessDeniedHandlerImpl
		implements AuthenticationSuccessHandler, AuthenticationFailureHandler, LogoutSuccessHandler {

	private final JwtTokenService tokenService;

	private final JwtTokenStore tokenStore;

	private final LibreSecurityProperties properties;

	private final ApplicationEventPublisher publisher;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) {
		if (response.isCommitted()) {
			return;
		}
		// 没有权限 403
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		RequestUtils.renderJson(response, R.fail("没有权限访问"));
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException e) {
		log.error(e.getMessage(), e);
		// 转换异常并且抛出给统一异常工具处理
		throw new LibreException(e.getMessage());
		// // 记录登录日志
		// SysLogEvent event = SysLogUtil.getSysLogDTO(SysLogType.Login);
		// // 异常详情
		// event.setExceptionDetail(Exceptions.getStackTraceAsString(e));
		// event.setDescription("登录失败");
		// event.setSuccessful(Boolean.FALSE);
		// event.setRequestTime(getRequestTime(request));
		// // 发送 spring event 事件
		// publisher.publishEvent(event);
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) {
		SecWebAuthenticationDetails details = (SecWebAuthenticationDetails) authentication.getDetails();
		// 用户信息
		AuthUser authUser = SecurityUtil.getUser(authentication);
		Date now = new Date();
		// 令牌有效期
		LibreSecurityProperties.JwtToken jwtToken = properties.getJwtToken();
		Duration expireTime = details.isRememberMe() ? jwtToken.getRememberMeTime() : jwtToken.getExpireTime();
		String token = tokenService.createToken(authUser, now, expireTime);
		// token 管理
		tokenStore.save(request, authUser, token, expireTime);

		JwtUser jwtUser = authUser.toJwtUser();
		jwtUser.setToken(token);

		RequestUtils.renderJson(response, R.data(jwtUser));
		// 记录登录日志
		SysLogEvent event = SysLogUtil.buildSysLogEvent(SysLogType.Login);

		event.setDescription("登录成功");
		event.setSuccess(SysLogConstant.SUCCESS);
		// 发送 spring event 事件
		publisher.publishEvent(event);
	}

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) {
		String token = tokenService.getToken(request);
		// 删除 token
		tokenStore.removeByToken(token);
		// 记录登出日志
		SysLogEvent event = SysLogUtil.buildSysLogEvent(SysLogType.Logout);
		// 从 token 中解析出登陆用户，此时 authentication 已经清空了
		// token 有超时解析错误等问题
		try {
			event.setUsername(tokenService.getSubject(token));
		}
		catch (Throwable e) {
			log.error(e.getMessage());
		}
		event.setDescription("登出成功");
		event.setSuccess(SysLogConstant.SUCCESS);
		// event.setRequestTime(getRequestTime(request));
		// 发送 spring event 事件
		publisher.publishEvent(event);
	}

	/**
	 * 获取请求时间
	 * @param request HttpServletRequest
	 * @return 请求时间
	 */
	// private static long getRequestTime(HttpServletRequest request) {
	// Long requestStartTime = (Long)
	// request.getAttribute(MicaConstant.REQUEST_START_TIME);
	// if (requestStartTime == null) {
	// return 0L;
	// }
	// return TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - requestStartTime);
	// }

}
