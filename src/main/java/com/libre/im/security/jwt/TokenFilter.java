package com.libre.im.security.jwt;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.libre.core.security.RsaUtil;
import com.libre.core.toolkit.StringUtil;
import com.libre.im.config.LibreSecurityProperties;
import com.libre.im.security.pojo.dto.OnlineUserDTO;
import com.libre.im.security.service.OnlineUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class TokenFilter extends GenericFilterBean {

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
		String token = resolveToken(httpServletRequest);
		// 对于 Token 为空的不需要去查 Redis
		if (StringUtil.isNotBlank(token)) {
			OnlineUserDTO onlineUserDto = null;
			try {
				LibreSecurityProperties.JwtToken jwtToken = properties.getJwtToken();
				onlineUserDto = onlineUserService.getOne(jwtToken.getStorePrefix() + token);
			}
			catch (Exception e) {
				log.error(e.getMessage());
			}

			if (onlineUserDto != null && StringUtils.hasText(token)) {
				Authentication authentication = tokenProvider.getAuthentication(token);
				SecurityContextHolder.getContext().setAuthentication(authentication);
				// Token 续期
				tokenProvider.checkRenewal(token);
			}
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

	/**
	 * 初步检测Token
	 * @param request /
	 * @return /
	 */
	private String resolveToken(HttpServletRequest request) {
		LibreSecurityProperties.JwtToken jwtToken = properties.getJwtToken();
		String bearerToken = request.getHeader(jwtToken.getHeader());
		if (StringUtils.hasText(bearerToken)) {
			if (bearerToken.startsWith(jwtToken.getStorePrefix())) {
				// 去掉令牌前缀
				String privateBase64 = RsaUtil.getPrivateBase64(properties.getLoginKeyPair());
				String token = bearerToken.replace(jwtToken.getStorePrefix(), StringPool.EMPTY);
				return RsaUtil.decryptFromBase64(privateBase64, token);
			}
			else {
				log.error("非法Token：{}", bearerToken);
			}
		}
		return null;
	}

}
