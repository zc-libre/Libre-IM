package com.libre.im.core.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ZC
 * @date 2021/8/1 15:21
 */
@Getter
@AllArgsConstructor
public enum MessageType {

    /**
     * 私聊
     */
    SINGLE(0),

    /**
     * 群聊
     */
    GROUP(1);

    private final Integer code;
}
