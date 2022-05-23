package com.libre.im.core.message.handler;

import com.libre.boot.autoconfigure.SpringContext;
import com.libre.im.core.channel.ChannelContext;
import com.libre.im.core.constant.LibreIMConstants;
import com.libre.im.core.message.ConnectType;
import com.libre.im.core.message.Message;
import com.libre.im.core.message.MessageBodyType;
import com.libre.im.core.message.TextMessage;
import com.libre.im.core.session.Session;
import com.libre.im.core.session.SessionManager;
import io.netty.channel.Channel;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * @author: Libre
 * @Date: 2022/4/30 10:54 PM
 */
public abstract class AbstractMessageHandler<T extends Message> implements MessageHandler<T> {
    protected final ChannelContext channelContext;
    protected final SessionManager sessionManager;

    public AbstractMessageHandler() {
        this.channelContext = Optional.ofNullable(SpringContext.getBean(ChannelContext.class))
                .orElseThrow(() -> new RuntimeException("channelContext not find"));
        this.sessionManager = Optional.ofNullable(SpringContext.getBean(SessionManager.class))
                .orElseThrow(() -> new RuntimeException("sessionManager not find"));
    }

    @Override
    public void resolveMessage(Message message) {
        sendMessage(message);
    }

    public void sendHeartBeatMessage(Long sessionId) {
        TextMessage textMessage = new TextMessage();
        textMessage.setCreateTime(LocalDateTime.now());
        textMessage.setBody(LibreIMConstants.PONG);
        textMessage.setMessageBodyType(MessageBodyType.TEXT.getCode());
        textMessage.setConnectType(ConnectType.HEART_BEAT.getType());
        Session session = sessionManager.getSession(sessionId);
        if (Objects.nonNull(session)) {
            Channel channel = session.getChannel();
            channel.writeAndFlush(textMessage);
        }
    }

    protected abstract void sendMessage(Message message);


}
