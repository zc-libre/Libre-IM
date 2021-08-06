package com.libre.im.core.pojo;

import lombok.Data;

/**
 * @author ZC
 * @date 2021/8/1 14:38
 */
@Data
public class ChatUser {

    private Long fromUserId;

    private String username;

    private Long toUserId;
}
