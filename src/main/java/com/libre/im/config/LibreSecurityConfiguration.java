package com.libre.im.config;

import com.google.common.collect.Sets;
import com.libre.im.security.annotation.AnonymousAccess;
import com.libre.im.security.support.JwtAccessDeniedHandler;
import com.libre.im.security.support.JwtAuthenticationEntryPoint;
import com.libre.im.security.jwt.TokenConfigurer;
import com.libre.im.security.jwt.TokenProvider;
import com.libre.im.security.service.OnlineUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

/**
 * @author Libre
 * @date 2021/7/11 20:20
 */
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(LibreSecurityProperties.class)
@Configuration(proxyBeanMethods = false)
public class LibreSecurityConfiguration  {

    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint authenticationErrorHandler;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final LibreSecurityProperties properties;
    private final ApplicationContext applicationContext;
    private final TokenProvider tokenProvider;
    private final OnlineUserService onlineUserService;


    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }

    @Bean
    SecurityFilterChain securityFilter(HttpSecurity http) throws Exception {
        // ?????????????????? url??? @AnonymousAccess
        RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) applicationContext.getBean("requestMappingHandlerMapping");
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = requestMappingHandlerMapping.getHandlerMethods();
        // ??????????????????
        Set<String> anonymousUrl = getAnonymousUrl(handlerMethodMap);
        List<String> permitAll = properties.getPermitAll();
         http
                .formLogin().disable()
                // ?????? CSRF
                .csrf().disable()
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                // ????????????
                .exceptionHandling()
                .authenticationEntryPoint(authenticationErrorHandler)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                // ??????iframe ????????????
                .and()
                .headers()
                .frameOptions()
                .disable()
                // ???????????????
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // ??????????????????
                .antMatchers(
                        HttpMethod.GET,
                        "/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/websocket/**"
                ).permitAll()
                // swagger ??????
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/*/api-docs").permitAll()
                // ??????
                .antMatchers("/avatar/**").permitAll()
                .antMatchers("/file/**").permitAll()
                // ???????????? druid
                .antMatchers("/druid/**").permitAll()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/captcha").permitAll()
                .antMatchers(anonymousUrl.toArray(new String[0])).permitAll()
                .antMatchers(permitAll.toArray(new String[0])).permitAll()
                // ??????OPTIONS??????
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest().authenticated()
                .and().apply(securityConfigurerAdapter());
         return http.build();
    }

    private Set<String> getAnonymousUrl(Map<RequestMappingInfo, HandlerMethod> handlerMethodMap) {
        Set<String> anonymousUrls = Sets.newHashSet();
         for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethodMap.entrySet()) {
             RequestMappingInfo requestMappingInfo = entry.getKey();
             HandlerMethod handlerMethod = entry.getValue();
             AnonymousAccess anonymousAccess = handlerMethod.getMethodAnnotation(AnonymousAccess.class);
             if (Objects.isNull(anonymousAccess) || Objects.isNull(requestMappingInfo.getPatternsCondition())) {
                 continue;
             }
             anonymousUrls.addAll(requestMappingInfo.getPatternsCondition().getPatterns());
         }
         return anonymousUrls;
    }


    private TokenConfigurer securityConfigurerAdapter() {
        return new TokenConfigurer(tokenProvider, properties, onlineUserService);
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
