package com.libre.im.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 用户角色表
 *
 * @author zhao.cheng
 */
@ApiModel(value = "用户角色表")
@Data
@TableName(value = "sys_user_role")
public class SysUserRole {

	/**
	 * 用户id
	 */
	@Schema(description = "用户id")
	private Long userId;

	/**
	 * 角色id
	 */
	@Schema(description = "角色id")
	private Long roleId;

}
