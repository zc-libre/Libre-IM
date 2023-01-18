package com.libre.im.system.pojo.dto;

import com.libre.toolkit.validation.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: Libre
 * @Date: 2022/9/11 3:23 PM
 */
@Data
public class RoleDTO implements Serializable {

	/**
	 * 主键 id
	 */
	@NotNull(groups = UpdateGroup.class)
	private Long id;

	/**
	 * 角色名称
	 */
	@NotBlank
	private String name;

	/**
	 * 角色权限字符串
	 */
	@NotBlank
	private String permission;

}
