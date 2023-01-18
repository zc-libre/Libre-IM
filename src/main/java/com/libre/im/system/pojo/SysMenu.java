package com.libre.im.system.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.libre.im.common.pojo.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单表
 *
 * @author zhao.cheng
 */
@ApiModel(value = "菜单表")
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_menu")
public class SysMenu extends BaseEntity {

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
	private Integer isFrame;

	/**
	 * 菜单类型（0目录 1菜单 2按钮）
	 */
	private Integer type;

	/**
	 * 缓存（0否 1是）
	 */
	private Integer cache;

	/**
	 * 显示状态（0显示，1隐藏）
	 */
	private Integer hidden;

	/**
	 * 菜单状态（0正常 1停用）
	 */
	private Integer status;

	/**
	 * 备注
	 */
	private String remark;

}
