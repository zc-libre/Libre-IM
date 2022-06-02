package com.libre.im.security.jwt;

import com.libre.im.config.LibreSecurityProperties;
import com.libre.im.security.service.OnlineUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author /
 */
@RequiredArgsConstructor
public class TokenConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	private final TokenProvider tokenProvider;

	private final LibreSecurityProperties properties;

	private final OnlineUserService onlineUserService;

	@Override
	public void configure(HttpSecurity http) {
		TokenFilter customFilter = new TokenFilter(tokenProvider, properties, onlineUserService);
		http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
	}

}
