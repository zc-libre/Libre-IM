package com.libre.im.core.message;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.libre.core.toolkit.JSONUtil;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;

/**
 * @author ZC
 * @date 2021/8/6 23:24
 */
@UtilityClass
public class MessageFactory {

    public static Message getMessage(String messageJson) {
        int type = getMessageType(messageJson);
        if (MessageType.SINGLE.getCode().equals(type)) {
            return JSONUtil.readValue(messageJson, SingleMessage.class);
        }else if (MessageType.GROUP.getCode().equals(type)) {
            return JSONUtil.readValue(messageJson, GroupMessage.class);
        }
        return null;
    }

    private static int getMessageType(String messageJson) {
        JsonNode jsonNode = JSONUtil.readTree(messageJson);
        return jsonNode.findValue("type").intValue();
    }
}
