package com.libre.im.core.message.handler;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.libre.im.core.channel.ChannelContext;
import com.libre.im.core.listener.OfflineMessageListener;
import com.libre.im.core.message.GroupMessage;
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

import java.util.*;

/**
 * @author ZC
 * @date 2021/8/7 14:06
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SingleMessageHandler implements MessageHandler, OfflineMessageListener {

    private static final String OFFLINE_MESSAGE_KEY = "offline_message_key";
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

        if (Objects.isNull(channel)) {
            sendOfflineMessage(message);
        }

        if (channel.isOpen()) {
            channel.writeAndFlush(new TextWebSocketFrame(singleMessage.getMessage()));
            log.debug("message had send: {}, send userId: {}, accept userId: {}",
                    singleMessage.getMessage(), singleMessage.getAcceptId(), singleMessage.getAcceptId());
        }
    }

    @Override
    public void sendOfflineMessage(Message message) {
        Long sendUserId = message.getSendUserId();
        WebsocketSession websocketSession = (WebsocketSession) sessionManager.get(sendUserId);
        Map<Long, List<Message>> offlineMessages = websocketSession.getAttribute(OFFLINE_MESSAGE_KEY);
        if (offlineMessages.containsKey(message.getSendUserId())) {
            List<Message> messages = offlineMessages.get(message.getSendUserId());
            for (Message msg : messages) {
                 if (msg instanceof SingleMessage) {
                     SingleMessage singleMessage = (SingleMessage) msg;
                     Channel channel = channelContext.getChannel(singleMessage.getAcceptId());
                     channel.writeAndFlush(message.getMessage());
                 }
            }
        }
    }

    @Override
    public void saveMessage(Message message) {
        WebsocketSession websocketSession = (WebsocketSession) sessionManager.get(message.getSendUserId());
        SingleMessage singleMessage = (SingleMessage) message;
        Map<Long, List<Message>> offlineMessages = websocketSession.getAttribute(OFFLINE_MESSAGE_KEY);
        List<Message> messages;
        if (Objects.isNull(offlineMessages)) {
            offlineMessages = Maps.newHashMap();
        }
        messages = offlineMessages.get(message.getSendUserId());
        if (CollUtil.isEmpty(messages)) {
            messages = Lists.newArrayList();
        }
        messages.add(singleMessage);
        offlineMessages.put(message.getSendUserId(), messages);
        websocketSession.addAttribute(OFFLINE_MESSAGE_KEY, offlineMessages);
    }
}
