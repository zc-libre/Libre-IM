package com.libre.im.security.jwt;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.libre.boot.toolkit.RequestUtils;
import com.libre.toolkit.result.R;
import com.libre.toolkit.core.StringUtil;
import com.libre.im.config.LibreSecurityProperties;
import com.libre.im.security.pojo.dto.OnlineUserDTO;
import com.libre.im.security.service.OnlineUserService;
import io.undertow.util.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class TokenFilter extends GenericFilterBean {

	public static final String TOKEN_PREFIX = "Bearer ";
	private final TokenProvider tokenProvider;

	private final LibreSecurityProperties properties;

	private final OnlineUserService onlineUserService;

	public TokenFilter(TokenProvider tokenProvider, LibreSecurityProperties properties,
			OnlineUserService onlineUserService) {
		this.tokenProvider = tokenProvider;
		this.properties = properties;
		this.onlineUserService = onlineUserService;
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		String token;
		try {
			token = resolveToken(httpServletRequest);
			if (StringUtil.isBlank(token)) {
				filterChain.doFilter(servletRequest, servletResponse);
				return;
			}
		} catch (BadRequestException e) {
			log.error("解析token失败",e);
			HttpServletResponse response = (HttpServletResponse) servletResponse;
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			RequestUtils.renderJson(response, R.fail("请重新登录"));
			return;
		}

		OnlineUserDTO onlineUserDto;
		try {
			onlineUserDto = onlineUserService.getOne(token);
		}
		catch (Exception e) {
			log.error("获取token失败",e);
			HttpServletResponse response = (HttpServletResponse) servletResponse;
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			RequestUtils.renderJson(response, R.fail("请重新登录"));
			return;
		}

		if (onlineUserDto != null && StringUtils.hasText(token)) {
			Authentication authentication = tokenProvider.getAuthentication(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			// Token 续期
			tokenProvider.checkRenewal(token);
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

	/**
	 * 初步检测Token
	 * @param request /
	 * @return /
	 */
	private String resolveToken(HttpServletRequest request) throws BadRequestException {
		LibreSecurityProperties.JwtToken jwtToken = properties.getJwtToken();
		String bearerToken = request.getHeader(jwtToken.getHeader());
		if (StringUtils.hasText(bearerToken)) {
			// 去掉令牌前缀
			return bearerToken.replace(TOKEN_PREFIX, StringPool.EMPTY);
		}
		return null;
	}

}
