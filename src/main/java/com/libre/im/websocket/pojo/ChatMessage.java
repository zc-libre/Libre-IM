package com.libre.im.websocket.pojo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: Libre
 * @Date: 2022/5/23 10:13 PM
 */
@Data
public class ChatMessage {

    private Long id;

    private String message;

    private Long sendUserId;

    private Long acceptUserId;

    private Integer type;

    private Integer status;

    private LocalDateTime createTime;
}
