package com.libre.im.system.pojo.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 前端菜单路由
 *
 * @author L.cm
 */
@Data
public class MenuVO implements Serializable {

	private Long id;

	private Long parentId;

	private String name;

	private String path;

	private Integer type;

	private Boolean hidden;

	private String redirect;

	private String component;

	private Integer alwaysShow;

	private MenuMetaVO meta;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<MenuVO> children;

}
