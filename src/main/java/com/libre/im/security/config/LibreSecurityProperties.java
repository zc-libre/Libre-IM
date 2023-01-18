package com.libre.im.security.config;

import com.google.common.collect.Lists;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.time.Duration;
import java.util.List;

/**
 * @author Libre
 * @date 2021/7/12 10:42
 */
@Data
@Validated
@ConfigurationProperties("libre.security")
public class LibreSecurityProperties {

	/**
	 * 忽略的地址
	 */
	private final List<String> permitAll = Lists.newArrayList();

	/**
	 * 登录配置
	 */
	@NestedConfigurationProperty
	private final Login login = new Login();

	/**
	 * token配置
	 */
	@NestedConfigurationProperty
	private final JwtToken jwtToken = new JwtToken();

	/**
	 * 登录配置
	 */
	@Getter
	@Setter
	public static class Login {

		/**
		 * 登录重试锁定次数，默认：5
		 */
		private int retryLimit = 5;

		/**
		 * 登录重试锁定cache名，默认：retryLimitCache
		 */
		private String retryLimitCacheName = "libre:login:retryLimit:";

		/**
		 * 限制登录时间
		 */
		private Duration retryLimitTime = Duration.ofMinutes(5);

	}

	/**
	 * 登录配置
	 */
	@Getter
	@Setter
	public static class JwtToken {

		/**
		 * 令牌自定义标识
		 */
		private String header = "Authorization";

		/**
		 * 令牌秘钥
		 */
		@NotBlank
		private String secret = "669da4ca0fde3928856705c8746512daa9957b91b0bbe855790b561d9f08be80";

		/**
		 * 秘钥的签名算法
		 */
		private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		/**
		 * jwt token 接受者
		 */
		private String audience = "Libre";

		/**
		 * jwt token 签发者
		 */
		private String issuer = "Libre";

		/**
		 * 令牌有效期（默认8小时)。
		 */
		private Duration expireTime = Duration.ofHours(8);

		/**
		 * 记住密码有效期（默认30天）
		 */
		private Duration rememberMeTime = Duration.ofDays(30);

		/**
		 * token 续期检查(单位：小时)
		 */
		private Duration detectTime = Duration.ofHours(4);

		/**
		 * 续期时间
		 */
		private Duration renewTime = Duration.ofHours(4);

		/**
		 * redis token 存储前缀
		 */
		private String storePrefix = "libre:login:token:";

	}

}
