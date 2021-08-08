package com.libre.im.core.message.handler;

import com.libre.im.core.channel.ChannelContext;
import com.libre.im.core.message.GroupMessage;
import com.libre.im.core.message.Message;
import com.libre.im.core.message.MessageType;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author ZC
 * @date 2021/8/7 14:07
 */
@Component
@RequiredArgsConstructor
public class GroupMessageHandler implements MessageHandler {

    private final ChannelContext channelContext;

    @Override
    public MessageType getMessageType() {
        return MessageType.GROUP;
    }

    @Override
    public void sendMessage(ChannelHandlerContext ctx, Message message) {
        GroupMessage groupMessage = (GroupMessage) message;
    }
}
