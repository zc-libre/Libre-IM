package com.libre.im.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.libre.im.common.pojo.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 用户表
 *
 * @author zhao.cheng
 */
@ApiModel(value = "用户表")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName(value = "sys_user")
public class SysUser extends BaseEntity {

	/**
	 * 账户
	 */
	@Schema(description = "账户")
	private String username;

	/**
	 * 密码
	 */
	@Schema(description = "密码")
	private String password;

	/**
	 * 昵称
	 */
	@Schema(description = "昵称")
	private String nickName;

	/**
	 * 头像
	 */
	@Schema(description = "头像")
	private String avatar;

	/**
	 * 电话
	 */
	@Schema(description = "电话")
	private String phone;

	/**
	 * 邮件
	 */
	@Schema(description = "邮件")
	private String email;

	/**
	 * 性别
	 */
	@Schema(description = "性别")
	private Integer gender;

	/**
	 * 用户类型（0系统用户 1管理员）
	 */
	@Schema(description = "用户类型（0系统用户 1管理员）")
	private Integer isAdmin;

	/**
	 * 帐号状态（0停用 1正常）
	 */
	@Schema(description = "帐号状态（0停用 1正常）")
	private Integer enabled;

	/**
	 * 登录状态（0:正常 1:锁定）
	 */
	@Schema(description = "登录状态")
	private Integer locked;

}
