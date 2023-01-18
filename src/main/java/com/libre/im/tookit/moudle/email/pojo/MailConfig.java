package com.libre.im.tookit.moudle.email.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author: Libre
 * @Date: 2023/1/12 1:31 AM
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "libre_mail_config")
public class MailConfig {

	private Long id;

	private String host;

	private Integer port;

	private String username;

	private String password;

	private String protocol;

	private String gmtModifiedName;

	private String gmtCreateName;

	private LocalDateTime gmtModified;

	private LocalDateTime gmtCreate;

}