package com.libre.im.security.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 在线用户
 * @author Zheng Jie
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnlineUserDTO {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 部門
     */
    private String dept;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * IP
     */
    private String ip;
    /**
     * 地址
     */
    private String address;
    /**
     * token
     */
    private String key;
    /**
     * 32 位 token 摘要
     */
    private String summary;
    /**
     * 登录时间
     */
    private LocalDateTime loginTime;


}
