package com.libre.im.system.pojo.entity;

import lombok.Data;

/**
 * <p>
 * 字典表
 * </p>
 *
 * @author L.cm
 * @since 2020-07-07
 */
@Data
public class SysDict {

	private Long id;

	/**
	 * 字典名称
	 */
	private String name;

	/**
	 * 字典描述
	 */
	private String description;

	/**
	 * 状态（0正常 1停用）
	 */
	private Integer status;

	/**
	 * 备注
	 */
	private String remark;

}
