package com.libre.im.system.pojo.dto;

import lombok.Data;

/**
 * 字典类型查询
 *
 * @author L.cm
 */
@Data
public class DictInfoCriteria {

	/**
	 * LIKE
	 */
	private String label;

	/**
	 * dict name
	 */
	private String name;

}
