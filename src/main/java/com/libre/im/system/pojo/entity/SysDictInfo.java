package com.libre.im.system.pojo.entity;

import lombok.Data;

/**
 * <p>
 * 字典详情表
 * </p>
 *
 * @author L.cm
 * @since 2020-07-07
 */
@Data
public class SysDictInfo {

	private Long id;

	/**
	 * 字典排序
	 */
	private Integer seq;

	/**
	 * 字典标签
	 */
	private String label;

	/**
	 * 字典键值
	 */
	private String value;

	/**
	 * 字典类型
	 */
	private String type;

	/**
	 * 样式属性（其他样式扩展）
	 */
	private String cssClass;

	/**
	 * 表格回显样式
	 */
	private String listClass;

	/**
	 * 是否默认（0否1是 ）
	 */
	private Integer isDefault;

	/**
	 * 状态（0正常 1停用）
	 */
	private Integer status;

	/**
	 * 备注
	 */
	private String remark;

}
