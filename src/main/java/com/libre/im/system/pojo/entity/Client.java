package com.libre.im.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.libre.im.common.pojo.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户端表
 *
 * @author zhao.cheng
 */
@ApiModel(value = "客户端表")
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_client")
public class Client extends BaseEntity {

	/**
	 * 客户端id
	 */
	@Schema(description = "客户端id")
	private String clientId;

	/**
	 * 客户端密钥
	 */
	@Schema(description = "客户端密钥")
	private String clientSecret;

	/**
	 * 资源集合
	 */
	@Schema(description = "资源集合")
	private String resourceIds;

	/**
	 * 授权范围
	 */
	@Schema(description = "授权范围")
	private String scope;

	/**
	 * 授权类型
	 */
	@Schema(description = "授权类型")
	private String authorizedGrantTypes;

	/**
	 * 回调地址
	 */
	@Schema(description = "回调地址")
	private String webServerRedirectUri;

	/**
	 * 权限
	 */
	@Schema(description = "权限")
	private String authorities;

	/**
	 * 令牌过期秒数
	 */
	@Schema(description = "令牌过期秒数")
	private Integer accessTokenValidity;

	/**
	 * 刷新令牌过期秒数
	 */
	@Schema(description = "刷新令牌过期秒数")
	private Integer refreshTokenValidity;

	/**
	 * 附加说明
	 */
	@Schema(description = "附加说明")
	private String additionalInformation;

	/**
	 * 自动授权
	 */
	@Schema(description = "自动授权")
	private String autoapprove;

	/**
	 * 状态
	 */
	@Schema(description = "状态")
	private Integer status;

}
