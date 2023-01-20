package com.libre.im.security.jwt;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.libre.boot.toolkit.RequestUtils;
import com.libre.im.common.security.dto.AuthUser;
import com.libre.im.security.config.LibreSecurityProperties;
import com.libre.im.security.pojo.vo.TokenVO;
import com.libre.ip2region.core.Ip2regionSearcher;
import com.libre.ip2region.core.IpInfo;
import com.libre.redis.cache.RedisUtils;
import com.libre.toolkit.core.CharPool;
import com.libre.toolkit.core.DesensitizationUtil;
import com.libre.toolkit.core.StringUtil;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * jwt token 存储
 *
 * @author L.cm
 */
@Configuration(proxyBeanMethods = false)
public class JwtTokenStore {

	private final RedisUtils redisCache;

	private final LibreSecurityProperties.JwtToken jwtTokenProperties;

	private final Ip2regionSearcher searcher;

	@Autowired
	public JwtTokenStore(RedisUtils redisCache, LibreSecurityProperties properties, Ip2regionSearcher searcher) {
		this.redisCache = redisCache;
		this.jwtTokenProperties = properties.getJwtToken();
		this.searcher = searcher;
	}

	/**
	 * 读取 token 信息
	 * @param token token
	 * @return TokenVO
	 */
	public TokenVO get(String token) {
		return redisCache.get(getCacheKeyByToken(token));
	}

	/**
	 * 存储 token
	 * @param request HttpServletRequest
	 * @param authUser AuthUser
	 * @param token token
	 * @param expireTime expireTime
	 */
	public void save(HttpServletRequest request, AuthUser authUser, String token, Duration expireTime) {

		String storePrefix = jwtTokenProperties.getStorePrefix();
		// key md5 避免太长，aes 后特别长
		String key = DigestUtils.md5Hex(token);
		String ip = RequestUtils.getIp(request);
		UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader(HttpHeaders.USER_AGENT));
		String browser = userAgent.getBrowser().getName();

		TokenVO tokenVo = new TokenVO();
		tokenVo.setUserId(authUser.getUserId());
		tokenVo.setUserName(authUser.getUsername());
		tokenVo.setNickName(authUser.getNickName());
		tokenVo.setBrowser(browser);
		tokenVo.setIp(ip);
		IpInfo ipInfo = searcher.memorySearch(ip);
		Optional.ofNullable(ipInfo).ifPresent(info -> tokenVo.setAddress(ipInfo.getAddress()));

		// token 摘要，前6后8，中间4位占位符
		tokenVo.setSummary(DesensitizationUtil.sensitive(token, 8, 8, CharPool.DOT, 4));
		tokenVo.setKey(key);
		tokenVo.setLoginTime(LocalDateTime.now());
		String cacheKey = storePrefix + key;

		redisCache.setEx(cacheKey, tokenVo, expireTime);
	}

	/**
	 * 读取所以的 token 信息
	 * @param filter 过滤条件
	 * @return token 结合
	 */
	public List<TokenVO> getAll(String filter) {
		String storePrefix = jwtTokenProperties.getStorePrefix();
		Set<String> tokenSet = redisCache.scan(storePrefix + CharPool.STAR);
		List<TokenVO> tokenVOList = new ArrayList<>();
		for (String cacheKey : tokenSet) {
			TokenVO tokenVo = redisCache.get(cacheKey);
			if (StringUtil.isNotBlank(filter)) {
				if (tokenVo.toString().contains(filter)) {
					tokenVOList.add(tokenVo);
				}
			}
			else {
				tokenVOList.add(tokenVo);
			}
		}
		tokenVOList.sort((o1, o2) -> o2.getLoginTime().compareTo(o1.getLoginTime()));
		return tokenVOList;
	}

	/**
	 * token 信息分页
	 * @param page page
	 * @param filter 过滤条件
	 * @return token 集合
	 */
	public Page<TokenVO> page(Page<TokenVO> page, String filter) {
		List<TokenVO> tokenVOList = getAll(filter);
		page.setRecords(tokenVOList);
		page.setTotal(tokenVOList.size());
		return page;
	}

	/**
	 * 批量移除 token
	 * @param keys key 集合
	 */
	@Async
	public void remove(Set<String> keys) {
		String storePrefix = jwtTokenProperties.getStorePrefix();
		for (String key : keys) {
			String cacheKey = storePrefix + key;
			redisCache.del(cacheKey);
		}
	}

	/**
	 * 批量移除 token
	 * @param token token
	 */
	@Async
	public void removeByToken(String token) {
		redisCache.del(getCacheKeyByToken(token));
	}

	private String getCacheKeyByToken(String token) {
		String storePrefix = jwtTokenProperties.getStorePrefix();
		String key = DigestUtils.md5Hex(token);
		return storePrefix + key;
	}

}
