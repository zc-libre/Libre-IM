package com.libre.im.core.message;

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
    TEXT(1),

    /**
     * 媒体
     */
    MEDIA(2);

    private final Integer code;

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
