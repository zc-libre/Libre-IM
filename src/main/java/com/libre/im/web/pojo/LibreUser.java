package com.libre.im.web.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author ZC
 * @date 2021/8/14 20:15
 */
@Data
@TableName("im_user")
public class LibreUser {

	@TableId
	private Long id;

	private String username;

	private String nikeName;

	private String avatar;

	private String password;

	private String chatCode;

	private String signature;

	private Integer age;

	private String address;

	private Integer gender;

	private String phone;

	private Boolean enabled;

	private Boolean locked;


}
