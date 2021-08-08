package com.libre.im.core.message.handler;

import com.libre.im.core.channel.ChannelContext;
import com.libre.im.core.message.Message;
import com.libre.im.core.message.MessageType;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author ZC
 * @date 2021/8/7 14:05
 */
public interface MessageHandler {

    /**
     * 获取对应消息类型
     * @return /
     */
    MessageType getMessageType();
    /**
     * 消息发送
     * @param ctx /
     * @param message /
     */
    void sendMessage(ChannelHandlerContext ctx, Message message);
}
