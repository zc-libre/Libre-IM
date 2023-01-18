package com.libre.im.system.pojo.dto;

import lombok.Data;

/**
 * @author: Libre
 * @Date: 2023/1/8 1:37 AM
 */
@Data
public class MenuDTO {

	private Long id;

	/**
	 * 父菜单ID
	 */
	private Long parentId;

	/**
	 * 菜单标题
	 */
	private String title;

	/**
	 * 菜单名称
	 */
	private String name;

	/**
	 * 显示顺序
	 */
	private Integer seq;

	/**
	 * 路由地址
	 */
	private String path;

	/**
	 * 权限标识
	 */
	private String permission;

	/**
	 * 组件路径
	 */
	private String component;

	/**
	 * 菜单图标
	 */
	private String icon;

	/**
	 * 是否为外链（0否 1是）
	 */
	private Boolean isFrame;

	/**
	 * 菜单类型（0目录 1菜单 2按钮）
	 */
	private Integer type;

	/**
	 * 缓存（0否 1是）
	 */
	private Boolean cache;

	/**
	 * 显示状态（0显示，1隐藏）
	 */
	private Boolean hidden;

	/**
	 * 菜单状态（0正常 1停用）
	 */
	private Boolean status;

	/**
	 * 备注
	 */
	private String remark;

}
