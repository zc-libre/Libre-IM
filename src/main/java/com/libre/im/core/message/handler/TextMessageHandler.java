package com.libre.im.core.message.handler;

import com.libre.im.core.message.Message;
import com.libre.im.core.message.MessageBodyType;
import com.libre.im.core.message.TextMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author ZC
 * @date 2021/8/7 14:06
 */
@Slf4j
public class TextMessageHandler extends AbstractMessageHandler<TextMessage> {
    @Override
    protected void sendMessage(ChannelHandlerContext ctx, Message message) {
        Long id = Optional.ofNullable(message.getAcceptUserId())
                .orElseThrow(() -> new IllegalArgumentException("acceptUserId not find"));
        Channel channel = channelContext.getChannel(id);
        channel.writeAndFlush(write(message));
    }

    @Override
    public MessageBodyType getMessageType() {
        return MessageBodyType.TEXT;
    }

    @Override
    public TextMessage newInstance() {
        return new TextMessage();
    }

    private TextMessage write(Message message) {
        TextMessage textMessage = new TextMessage();
        textMessage.setCreateTime(LocalDateTime.now());
        textMessage.setBody(message.getBody());
        textMessage.setAcceptUserId(message.getSendUserId());
        textMessage.setMessageBodyType(MessageBodyType.TEXT.getCode());
        textMessage.setSendUserId(message.getAcceptUserId());
        return textMessage;
    }

}
