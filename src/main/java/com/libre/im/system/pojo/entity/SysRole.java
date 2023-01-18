package com.libre.im.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.libre.im.common.pojo.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色表
 *
 * @author zhao.cheng
 */
@ApiModel(value = "角色表")
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_role")
public class SysRole extends BaseEntity {

	/**
	 * 角色名
	 */
	@Schema(description = "角色名")
	private String roleName;

	/**
	 * 父id
	 */
	@Schema(description = "父id")
	private Long parentId;

	/**
	 * 角色状态（0正常 1停用）
	 */
	@Schema(description = "角色状态（0正常 1停用）")
	private Integer status;

	/**
	 * 角色权限字符串
	 */
	private String permission;

	private Integer seq;

}
