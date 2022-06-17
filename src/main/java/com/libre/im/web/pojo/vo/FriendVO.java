package com.libre.im.web.pojo.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: Libre
 * @Date: 2022/5/3 7:52 AM
 */
@Data
public class FriendVO {

    private Long friendId;

    private String username;

    private String nikeName;

    private String avatar;

    private String chatCode;

    private String signature;

    private Integer age;

    private String address;

    private Integer gender;

    private Boolean isTop;

    private LocalDateTime addTime;

}
