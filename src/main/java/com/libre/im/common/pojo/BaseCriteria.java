package com.libre.im.common.pojo;

import com.libre.toolkit.core.StringUtil;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Libre
 * @date 2022/1/1 18:20
 */
@Data
public abstract class BaseCriteria {

	private Long id;

	/**
	 * 模糊搜索字段
	 */
	private String blurry;

	/**
	 * 创建时间
	 */
	private List<LocalDateTime> createTime;

	public boolean isBlurryQuery() {
		return StringUtil.isNotBlank(blurry);
	}

	public boolean haveTime() {
		return CollectionUtils.isNotEmpty(this.createTime) && this.createTime.size() == 2;
	}

	public LocalDateTime getStartTime() {
		return this.createTime.get(0);
	}

	public LocalDateTime getEndTime() {
		return this.createTime.get(1);
	}

}
