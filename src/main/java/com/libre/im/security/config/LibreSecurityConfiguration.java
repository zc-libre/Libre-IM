package com.libre.im.security.config;

import com.libre.im.security.auth.SecAuthHandler;
import com.libre.im.security.auth.SecAuthenticationProvider;
import com.libre.im.security.auth.SecWebAuthDetailsSource;
import com.libre.im.security.jwt.JwtAuthenticationTokenFilter;
import com.libre.im.security.service.UserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Spring Security 权限控制
 *
 */
@EnableWebSecurity
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(LibreSecurityProperties.class)
public class LibreSecurityConfiguration implements SmartInitializingSingleton {

	private final UserDetailServiceImpl userDetailsService;

	private final SecAuthHandler authHandler;

	private final SecWebAuthDetailsSource authDetailsSource;

	private final LibreSecurityProperties properties;

	private final CacheManager cacheManager;

	private final JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

	private final ApplicationContext applicationContext;

	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	@Bean
	GrantedAuthorityDefaults grantedAuthorityDefaults() {
		return new GrantedAuthorityDefaults("");
	}

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		// @formatter:off
		// 关闭 csrf、iframe、session
		http.csrf()
			.disable()
			.headers()
			.frameOptions()
			.disable()
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.authorizeRequests()
				.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				.antMatchers(properties.getPermitAll().toArray(new String[0])).permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.exceptionHandling()
			.accessDeniedHandler(authHandler);

		http.formLogin()
			.loginPage("/")
			.loginProcessingUrl("/api/auth/token")
			.failureHandler(authHandler)
			.successHandler(authHandler)
			.authenticationDetailsSource(authDetailsSource)
			.permitAll()
			.and()
			.logout()
			.logoutUrl("/api/auth/logout")
			.clearAuthentication(false)
			.logoutSuccessHandler(authHandler)
			.logoutSuccessUrl("/");

		// jwt 认证的 filter
		http.addFilterAt(jwtAuthenticationTokenFilter, BasicAuthenticationFilter.class);
		// @formatter:on
		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public SecAuthenticationProvider authProvider() {
		final SecAuthenticationProvider authProvider = new SecAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setUserDetailsPasswordService(userDetailsService);

		authProvider.setMicaSecurityProperties(properties);
		authProvider.setCacheManager(cacheManager);
		authProvider.setPasswordEncoder(applicationContext.getBean(PasswordEncoder.class));
		return authProvider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Override
	public void afterSingletonsInstantiated() {
		authenticationManagerBuilder
				.authenticationProvider(applicationContext.getBean(SecAuthenticationProvider.class));
		authenticationManagerBuilder.eraseCredentials(false);
	}

}
