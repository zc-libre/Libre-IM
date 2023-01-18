package com.libre.im.common.security.dto;

import lombok.Data;

import java.util.List;

/**
 * jwt token 中存储的用户新
 *
 * @author L.cm
 */
@Data
public class JwtUser {

	/**
	 * 用户id
	 */
	private Long id;

	private String token;

	/**
	 * 登录名
	 */
	private String username;

	/**
	 * 用户昵称
	 */
	private String nickName;

	/**
	 * 用户性别（0男 1女 2未知）
	 */
	private Integer gender;

	/**
	 * 头像地址
	 */
	private String avatar;

	/**
	 * 用户邮箱
	 */
	private String email;

	/**
	 * 手机号码
	 */
	private String phone;

	/**
	 * 是否admin
	 */
	private Integer isAdmin;

	/**
	 * 角色信息
	 */
	private List<RoleInfo> roles;

	/**
	 * 角色信息
	 */
	private List<String> roleList;

}
