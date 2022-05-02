package com.libre.im.core.message.handler;

import com.libre.boot.autoconfigure.SpringContext;
import com.libre.im.core.channel.ChannelContext;
import com.libre.im.core.constant.LibreIMConstants;
import com.libre.im.core.message.ConnectType;
import com.libre.im.core.message.Message;
import com.libre.im.core.message.MessageBodyType;
import com.libre.im.core.message.TextMessage;
import com.libre.im.core.session.SessionManager;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author: Libre
 * @Date: 2022/4/30 10:54 PM
 */
public abstract class AbstractMessageHandler<T extends Message> implements MessageHandler<T> {

    protected static final String OFFLINE_MESSAGE_KEY = "OFFLINE_MESSAGE_KEY";
    protected final ChannelContext channelContext;
    protected final SessionManager sessionManager;

    protected Message message;

    public AbstractMessageHandler() {
        this.channelContext = Optional.ofNullable(SpringContext.getBean(ChannelContext.class))
                .orElseThrow(() -> new RuntimeException("channelContext not find"));

        this.sessionManager = SpringContext.getBean(SessionManager.class);
    }

    @Override
    public void resolveMessage(ChannelHandlerContext ctx, Message message) {
        Integer connectType = message.getConnectType();
        if (ConnectType.HEART_BEAT.getType().equals(connectType)) {
            TextMessage textMessage = new TextMessage();
            textMessage.setCreateTime(LocalDateTime.now());
            textMessage.setBody(LibreIMConstants.PONG);
            textMessage.setMessageBodyType(MessageBodyType.TEXT.getCode());
            textMessage.setConnectType(ConnectType.HEART_BEAT.getType());
            ctx.channel().writeAndFlush(textMessage);
        } else if (ConnectType.SEND.getType().equals(connectType)) {
            sendMessage(ctx, message);
        }
    }

    protected abstract void sendMessage(ChannelHandlerContext ctx, Message message);


}
