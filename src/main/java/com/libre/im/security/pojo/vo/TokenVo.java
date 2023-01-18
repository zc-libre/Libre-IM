package com.libre.im.security.pojo.vo;

import com.libre.toolkit.json.JsonUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * token vo
 *
 * @author L.cm
 */
@Getter
@Setter
public class TokenVo implements Serializable {

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 岗位
	 */
	private String dept;

	/**
	 * 浏览器
	 */
	private String browser;

	/**
	 * IP
	 */
	private String ip;

	/**
	 * 地址
	 */
	private String address;

	/**
	 * aes(token) = key
	 */
	private String key;

	/**
	 * 32 位 token 摘要
	 */
	private String summary;

	/**
	 * 登录时间
	 */
	private LocalDateTime loginTime;

	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}

}
