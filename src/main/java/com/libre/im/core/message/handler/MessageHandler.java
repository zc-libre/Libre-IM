package com.libre.im.core.message.handler;

import com.google.protobuf.MessageLite;
import com.libre.im.core.message.Message;
import com.libre.im.core.message.MessageBodyType;
import com.libre.im.core.proto.TextMessageProto;
import io.netty.channel.ChannelHandlerContext;

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
     * @param ctx     {@link ChannelHandlerContext}
     * @param message T
     */
    void resolveMessage(ChannelHandlerContext ctx, Message message);
}
