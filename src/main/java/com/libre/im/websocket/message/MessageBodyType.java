package com.libre.im.websocket.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ZC
 * @date 2021/8/1 15:21
 */
@Getter
@AllArgsConstructor
public enum MessageBodyType {

    /**
     * 文本
     */
    TEXT(1, TextMessage.class),

    /**
     * 媒体
     */
    MEDIA(2, MediaMessage.class);

    private final Integer code;
    private final Class<?> clazz;

    public static MessageBodyType find(Integer code) {
        MessageBodyType[] values = MessageBodyType.values();
        for (MessageBodyType message : values) {
            if (message.getCode().equals(code)) {
                return message;
            }
        }
        return null;
    }
}
