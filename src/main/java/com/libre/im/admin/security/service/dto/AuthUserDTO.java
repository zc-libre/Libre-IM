package com.libre.im.admin.security.service.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author Zheng Jie
 * @date 2018-11-30
 */
@Getter
@Setter
public class AuthUserDTO {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String code;

    private String uuid = "";
}
