package com.libre.im.security.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Libre
 * @date 2021/7/12 10:23
 */
@Data
public class DeptInfo implements Serializable {

	/**
	 * 主键ID
	 */
	private Long id;

	/**
	 * 部门名称
	 */
	private String name;

}
