package com.libre.im.websocket.message.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: Libre
 * @Date: 2022/5/5 3:38 AM
 */
@Getter
@AllArgsConstructor
public enum MessageStatus {

    ONLINE(1),

    OFFLINE(2),
    ;

    private final Integer status;

    public static MessageStatus find(Integer code) {
        MessageStatus[] values = MessageStatus.values();
        for (MessageStatus messageStatus : values) {
            if (messageStatus.getStatus().equals(code)) {
                return messageStatus;
            }
        }
        return null;
    }
}
