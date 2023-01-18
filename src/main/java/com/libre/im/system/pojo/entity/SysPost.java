package com.libre.im.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.libre.im.common.pojo.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 岗位表
 *
 * @author zhao.cheng
 */
@ApiModel(value = "岗位表")
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_post")
public class SysPost extends BaseEntity {

	/**
	 * 部门名称
	 */

	@Schema(description = "部门名称")
	private String postName;

	/**
	 * 排序
	 */

	@Schema(description = "排序")
	private String sort;

	/**
	 * 状态
	 */

	@Schema(description = "状态")
	private Integer status;

}
