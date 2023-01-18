package com.libre.im.system.pojo.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 角色创建更新实体
 *
 * @author L.cm
 */
@Data
public class RoleVO {

	/**
	 * 主键 id
	 */
	private Long id;

	/**
	 * 角色名称
	 */
	private String roleName;

	/**
	 * 角色权限字符串
	 */
	private String permission;

	private Integer seq;

	/**
	 * 创建时间
	 */
	private LocalDateTime gmtCreate;

}
