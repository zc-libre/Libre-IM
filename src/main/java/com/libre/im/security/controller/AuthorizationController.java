package com.libre.im.security.controller;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.libre.captcha.service.CaptchaService;
import com.libre.captcha.vo.CaptchaVO;
import com.libre.core.result.R;
import com.libre.core.security.RsaUtil;
import com.libre.core.toolkit.StringUtil;
import com.libre.im.config.LibreSecurityProperties;
import com.libre.im.security.annotation.AnonymousAccess;
import com.libre.im.security.jwt.TokenProvider;
import com.libre.im.security.pojo.dto.AuthUser;
import com.libre.im.security.pojo.dto.AuthUserDTO;
import com.libre.im.security.pojo.vo.JwtUserVO;
import com.libre.im.security.service.OnlineUserService;
import com.libre.im.security.service.UserLockService;
import com.libre.redis.cache.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.undertow.util.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Libre
 * @date 2021/7/12 17:54
 */
@Slf4j
@Api(tags = "授权管理")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthorizationController {

	private final LibreSecurityProperties properties;

	private final CaptchaService captchaService;

	private final RedisUtils redisUtils;

	private final OnlineUserService onlineUserService;

	private final TokenProvider tokenProvider;

	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	private final UserLockService userLockService;

	@AnonymousAccess
	@ApiOperation("获取验证码")
	@GetMapping("/captcha")
	public R<CaptchaVO> captcha() {
		CaptchaVO captchaVO = captchaService.generateBase64Vo(IdWorker.get32UUID());
		return R.data(captchaVO);
	}

	@AnonymousAccess
	@ApiOperation("密码加密公钥")
	@GetMapping("/public-key")
	public R<String> getPublicKey() {
		return R.data(RsaUtil.getPublicBase64(properties.getLoginKeyPair()));
	}

	@AnonymousAccess
	@ApiOperation("登录")
	@PostMapping("/token")
	public R<JwtUserVO> login(@Validated @RequestBody AuthUserDTO authUser, HttpServletRequest request)
			throws BadRequestException {
		boolean validate = captchaService.validate(authUser.getUuid(), authUser.getCode());
		if (StringUtil.isBlank(authUser.getCode()) || !validate) {
			throw new BadRequestException("验证码错误");
		}

		String privateBase64 = RsaUtil.getPrivateBase64(properties.getLoginKeyPair());
		String password = RsaUtil.decryptFromBase64(privateBase64, authUser.getPassword());

		String retryLimitCacheName = properties.getLogin().getRetryLimitCacheName();
		String username = authUser.getUsername();
		retryLimitCacheName = retryLimitCacheName + username;
		Integer retryCount = redisUtils.get(retryLimitCacheName);
		if (null == retryCount) {
			retryCount = 1;
			redisUtils.set(retryLimitCacheName, retryCount);
		}
		int retryLimit = properties.getLogin().getRetryLimit();
		if (retryCount > retryLimit) {
			log.warn("username: " + username + " tried to login more than " + retryLimit + " times in period");
			userLockService.updateLockUser(authUser);
			throw new LockedException(String.format("登录错误%d次，账号已锁定", retryCount));
		}
		else {
			redisUtils.incr(retryLimitCacheName);
		}

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				authUser.getUsername(), password);
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = tokenProvider.createToken(authentication);
		final AuthUser jwtUserDto = (AuthUser) authentication.getPrincipal();
		// 保存在线信息
		onlineUserService.save(jwtUserDto, token, request);
		redisUtils.del(retryLimitCacheName);
		JwtUserVO jwtUserVO = new JwtUserVO();
		jwtUserVO.setUserInfo(jwtUserDto.toJwtUser());
		jwtUserVO.setToken(token);
		jwtUserVO.setPublicKey(RsaUtil.getPublicBase64(properties.getUserKeyPair()));
		return R.data(jwtUserVO);
	}

	@ApiOperation("获取用户信息")
	@GetMapping("/info")
	public R<JwtUserVO> getUserInfo(AuthUser authUser) {
		JwtUserVO userVO = new JwtUserVO();
		userVO.setUserInfo(authUser.toJwtUser());
		userVO.setPublicKey(RsaUtil.getPublicBase64(properties.getUserKeyPair()));
		return R.data(userVO);
	}

	@ApiOperation("退出登录")
	@DeleteMapping(value = "/logout")
	public R<Boolean> logout(HttpServletRequest request) {
		String token = tokenProvider.getToken(request);
		onlineUserService.removeByToken(token);
		return R.data(Boolean.TRUE);
	}

}
