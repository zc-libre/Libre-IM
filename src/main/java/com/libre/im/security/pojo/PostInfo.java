package com.libre.im.security.pojo;

import lombok.Data;
import org.springframework.lang.NonNull;

import java.io.Serializable;

/**
 * 岗位信息
 *
 * @author Libre
 */
@Data
public class PostInfo implements Serializable {

	/**
	 * 主键ID
	 */
	@NonNull
	private Long id;

	/**
	 * 岗位编码
	 */
	private String code;

	/**
	 * 岗位名称
	 */
	private String name;

}
