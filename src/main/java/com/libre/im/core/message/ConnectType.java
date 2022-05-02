package com.libre.im.core.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ConnectType {

    HEART_BEAT(1),

    SEND(2);

    private final Integer type;

    public static ConnectType find(Integer code) {
        ConnectType[] values = ConnectType.values();
        for (ConnectType connectType : values) {
            if (connectType.getType().equals(code)) {
                return connectType;
            }
        }
        return null;
    }

}