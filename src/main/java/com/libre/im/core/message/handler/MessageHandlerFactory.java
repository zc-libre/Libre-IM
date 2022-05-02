package com.libre.im.core.message.handler;

import com.libre.im.core.message.MessageBodyType;

/**
 * @author ZC
 * @date 2021/8/7 14:36
 */
public class MessageHandlerFactory {

    @SuppressWarnings("unchecked")
    public static <T extends MessageHandler<?>> T getMessageHandler(int type) {
        MessageBodyType messageBodyType = MessageBodyType.find(type);
        if (MessageBodyType.TEXT.equals(messageBodyType)) {
            return (T) new TextMessageHandler();
        } else if (MessageBodyType.MEDIA.equals(messageBodyType)) {
            return (T) new MediaMessageHandler();
        }
        throw new IllegalArgumentException("非法类型");
    }
}
