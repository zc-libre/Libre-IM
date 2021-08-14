package com.libre.im.web.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ZC
 * @date 2021/8/14 20:15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LibreUser extends BaseEntity{

    private String username;

    @JsonIgnore
    private String password;

    private String sign;

    private String age;

    private String address;

    private String avatar;

}
