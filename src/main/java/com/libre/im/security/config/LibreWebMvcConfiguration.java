package com.libre.im.security.config;

import com.libre.im.common.config.PageArgumentResolver;
import com.libre.im.security.auth.AuthUserArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author Libre
 * @date 2021/7/12 16:02
 */
@Configuration(proxyBeanMethods = false)
public class LibreWebMvcConfiguration implements WebMvcConfigurer {

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new AuthUserArgumentResolver());
		argumentResolvers.add(new PageArgumentResolver());
	}

}
