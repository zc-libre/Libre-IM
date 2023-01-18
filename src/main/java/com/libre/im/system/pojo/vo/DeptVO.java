package com.libre.im.system.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Libre
 * @date 2022/2/2 15:43
 */
@Data
public class DeptVO implements Serializable {

	private Long id;

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

	/**
	 * 子部门
	 */
	private List<DeptVO> children;

}
