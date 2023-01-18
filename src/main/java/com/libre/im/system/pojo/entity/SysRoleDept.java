package com.libre.im.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 角色部门表
 *
 * @author zhao.cheng
 */
@ApiModel(value = "角色部门表")
@Data
@TableName(value = "sys_role_dept")
public class SysRoleDept {

	/**
	 * 角色id
	 */
	@Schema(description = "角色id")
	private Long roleId;

	/**
	 * 部门id
	 */
	@Schema(description = "部门id")
	private Long deptId;

}
