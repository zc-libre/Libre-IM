package com.libre.im.system.pojo.vo;

import com.libre.toolkit.json.JsonUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Libre
 * @date 2021/7/12 12:21
 */
@Getter
@Setter
public class TokenVO implements Serializable {

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
