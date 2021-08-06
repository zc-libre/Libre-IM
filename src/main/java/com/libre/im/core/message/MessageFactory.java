package com.libre.im.core.message;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author ZC
 * @date 2021/8/6 23:24
 */
public class MessageFactory {

    private final static Map<Integer, Message> MESSAGE_MAP = Maps.newHashMap();

    static {
        MESSAGE_MAP.put(MessageType.SINGLE.getCode(), new SingleMessage());
        MESSAGE_MAP.put(MessageType.GROUP.getCode(), new GroupMessage());
    }

    public static Message getMessage(int type) {
       return MESSAGE_MAP.get(type);
    }
}
