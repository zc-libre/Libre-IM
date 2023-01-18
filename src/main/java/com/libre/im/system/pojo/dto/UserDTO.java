package com.libre.im.system.pojo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
@Data
public class UserDTO implements Serializable {

	private Long id;

	private String username;

	private String nickName;

	private String email;

	private String phone;

	private String gender;

	private String avatarName;

	private String avatarPath;

	@JsonIgnore
	private String password;

	private Integer enabled;

	private List<Long> roleIds;

}
