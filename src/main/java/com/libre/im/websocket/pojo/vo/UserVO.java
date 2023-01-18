package com.libre.im.websocket.pojo.vo;

import lombok.Data;

/**
 * @author: Libre
 * @Date: 2022/6/21 9:48 PM
 */
@Data
public class UserVO {

    private Long id;

    private String username;

    private String nikeName;

    private String avatar;

    private String chatCode;

    private String signature;

    private Integer age;

    private String address;

    private Integer gender;

    private String phone;

}
