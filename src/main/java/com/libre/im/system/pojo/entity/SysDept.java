package com.libre.im.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.libre.im.common.pojo.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门表
 *
 * @author zhao.cheng
 */
@ApiModel(value = "部门表")
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_dept")
public class SysDept extends BaseEntity {

	/**
	 * 父id
	 */
	@Schema(description = "父id")
	private Long parentId;

	/**
	 * 部门名称
	 */
	@Schema(description = "部门名称")
	private String deptName;

}
