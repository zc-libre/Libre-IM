package com.libre.im.core.message.handler;

import com.libre.im.core.channel.ChannelContext;
import com.libre.im.core.message.Message;
import com.libre.im.core.message.MessageType;
import com.libre.im.core.message.SingleMessage;
import com.libre.im.core.session.Session;
import com.libre.im.core.session.SessionManager;
import com.libre.im.core.session.WebsocketSession;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author ZC
 * @date 2021/8/7 14:06
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SingleMessageHandler implements MessageHandler {

    private final ChannelContext channelContext;
    private final SessionManager sessionManager;

    @Override
    public MessageType getMessageType() {
        return MessageType.SINGLE;
    }

    @Override
    public void sendMessage(ChannelHandlerContext ctx, Message message) {
        SingleMessage singleMessage = (SingleMessage) message;
        Channel channel = channelContext.getChannel(singleMessage.getAcceptId());
        if (Objects.nonNull(channel)) {
            if (channel.isOpen()) {
                channel.writeAndFlush(new TextWebSocketFrame(singleMessage.getMessage()));
                log.debug("message had send: {}, send userId: {}, accept userId: {}",
                        singleMessage.getMessage(), singleMessage.getFrom(), singleMessage.getAcceptId());
            }
        }
    }
}
