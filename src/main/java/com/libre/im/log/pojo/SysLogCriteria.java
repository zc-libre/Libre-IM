package com.libre.im.log.pojo;

import com.libre.im.common.pojo.BaseCriteria;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Libre
 * @date 2022/2/2 19:16
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SysLogCriteria extends BaseCriteria {

	private Integer success;

	private Long userId;

}
