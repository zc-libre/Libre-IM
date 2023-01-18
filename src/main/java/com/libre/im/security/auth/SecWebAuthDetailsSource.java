package com.libre.im.security.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * 授权详情 添加验证码
 *
 * @author L.cm
 */
@Configuration(proxyBeanMethods = false)
public class SecWebAuthDetailsSource
		implements AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> {

	@Override
	public WebAuthenticationDetails buildDetails(HttpServletRequest context) {
		return new SecWebAuthenticationDetails(context);
	}

}
