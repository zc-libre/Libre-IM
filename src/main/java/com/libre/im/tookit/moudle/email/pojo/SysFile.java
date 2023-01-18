package com.libre.im.tookit.moudle.email.pojo;

import com.libre.im.common.pojo.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author: Libre
 * @Date: 2023/1/18 12:05 AM
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysFile extends BaseEntity {

	private Long id;

	@ApiModelProperty(value = "真实文件名")
	private String realName;

	@ApiModelProperty(value = "文件名")
	private String name;

	@ApiModelProperty(value = "后缀")
	private String suffix;

	@ApiModelProperty(value = "路径")
	private String path;

	@ApiModelProperty(value = "类型")
	private String type;

	@ApiModelProperty(value = "大小")
	private String size;

}
