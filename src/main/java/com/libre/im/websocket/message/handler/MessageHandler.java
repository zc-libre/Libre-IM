package com.libre.im.websocket.message.handler;

import com.google.protobuf.MessageLite;
import com.libre.im.websocket.message.Message;
import com.libre.im.websocket.message.MessageBodyType;

/**
 * @author ZC
 * @date 2021/8/7 14:05
 */
public interface MessageHandler<T extends Message> {

    /**
     * 获取对应消息类型
     *
     * @return MessageBodyType
     */
    MessageBodyType getMessageType();

    /**
     * 创建新Message对象
     *
     * @return t
     */
    T newInstance();

    MessageLite getMessage(Message message);
    /**
     * 消息发送
     *
     * @param message T
     */
    void resolveMessage(Message message);
}
