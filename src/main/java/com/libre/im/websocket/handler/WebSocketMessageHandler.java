package com.libre.im.websocket.handler;

import com.libre.boot.autoconfigure.SpringContext;
import com.libre.boot.exception.ErrorType;
import com.libre.boot.exception.ErrorUtil;
import com.libre.boot.exception.LibreErrorEvent;
import com.libre.core.toolkit.StringUtil;
import com.libre.im.config.WebsocketServerProperties;
import com.libre.im.websocket.constant.LibreIMConstants;
import com.libre.im.websocket.mapstruct.MessageMapping;
import com.libre.im.websocket.message.enums.ConnectType;
import com.libre.im.websocket.message.enums.MessageBodyType;
import com.libre.im.websocket.message.TextMessage;
import com.libre.im.websocket.message.handler.MessageHandler;
import com.libre.im.websocket.message.handler.MessageHandlerFactory;
import com.libre.im.websocket.message.handler.TextMessageHandler;
import com.libre.im.websocket.proto.TextMessageProto;
import com.libre.im.websocket.session.Session;
import com.libre.im.websocket.session.SessionManager;
import com.libre.im.websocket.session.WebsocketSession;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.time.Clock;

/**
 * @author ZC
 * @date 2021/8/8 0:03
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ChannelHandler.Sharable
public class WebSocketMessageHandler extends SimpleChannelInboundHandler<TextMessageProto.TextMessage> {

    protected final WebsocketServerProperties properties;
    private final SessionManager sessionManager;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        String sessionIdStr = ctx.channel().attr(LibreIMConstants.SERVER_SESSION_ID).get();
        if (StringUtil.isNotBlank(sessionIdStr)) {
            long sessionId = Long.parseLong(sessionIdStr);
            //发送心跳包
            if (evt instanceof IdleStateEvent && ((IdleStateEvent) evt).state().equals(IdleState.WRITER_IDLE)) {
                sendHeartBeatMessage(sessionId);
            }

            if (evt instanceof IdleStateEvent && ((IdleStateEvent) evt).state().equals(IdleState.READER_IDLE)) {
                long lastTime = Long.parseLong(ctx.channel().attr(LibreIMConstants.SERVER_SESSION_ID).get());
                if ((Clock.systemDefaultZone().millis() - lastTime) / 1000 >= 60) {
                    sessionManager.remove(sessionId);
                }
            }
        }

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextMessageProto.TextMessage msg) throws Exception {
        log.debug("message received: {}", msg);
        Session session;
        long sessionId = msg.getSendUserId();
        if (!sessionManager.isExist(sessionId)) {
            session = WebsocketSession.of(ctx, sessionId);
            sessionManager.put(sessionId, session);
        } else {
            session = sessionManager.getSession(sessionId);
        }
        session.addAttribute(LibreIMConstants.SERVER_SESSION_HEART_BEAT_KEY, Clock.systemDefaultZone().millis());
        if (ConnectType.HEART_BEAT.getType() == msg.getConnectType()) {
            sendHeartBeatMessage(sessionId);
        } else {
            MessageHandler<?> messageHandler = MessageHandlerFactory.getMessageHandler(msg.getMessageBodyType());
            MessageMapping mapping = MessageMapping.INSTANCE;
            TextMessage textMessage = mapping.sourceToTarget(msg);
            messageHandler.resolveMessage(textMessage);
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.debug("channel add {}", ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.debug("channel removed: {}", ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.debug("channel Inactive: {}", ctx.channel());
        sessionManager.remove(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LibreErrorEvent event = new LibreErrorEvent();
        event.setErrorType(ErrorType.WEB_SOCKET);
        ErrorUtil.initErrorInfo(cause, event);
        ApplicationContext context = SpringContext.getContext();
        context.publishEvent(event);
        log.error("error: {}", event);
        sessionManager.remove(ctx);
        ctx.close();
    }

    private void sendHeartBeatMessage(long sessionId) {
        TextMessageHandler messageHandler = MessageHandlerFactory.getMessageHandler(MessageBodyType.TEXT.getCode());
        messageHandler.sendHeartBeatMessage(sessionId);
    }
}
