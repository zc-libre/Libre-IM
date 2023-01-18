package com.libre.im.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 用户岗位表
 *
 * @author zhao.cheng
 */
@ApiModel(value = "用户岗位表")
@Data
@TableName(value = "sys_user_post")
public class SysUserPost {

	/**
	 * 用户id
	 */
	@Schema(description = "用户id")
	private Long userId;

	/**
	 * 岗位id
	 */
	@Schema(description = "岗位id")
	private Long postId;

}
