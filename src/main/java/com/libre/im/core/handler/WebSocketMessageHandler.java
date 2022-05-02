package com.libre.im.core.handler;

import com.libre.boot.autoconfigure.SpringContext;
import com.libre.boot.exception.ErrorType;
import com.libre.boot.exception.ErrorUtil;
import com.libre.boot.exception.LibreErrorEvent;
import com.libre.core.json.JsonUtil;
import com.libre.im.core.config.WebsocketServerProperties;
import com.libre.im.core.mapstruct.MessageMapping;
import com.libre.im.core.message.TextMessage;
import com.libre.im.core.message.handler.MessageHandler;
import com.libre.im.core.message.handler.MessageHandlerFactory;
import com.libre.im.core.proto.TextMessageProto;
import com.libre.im.core.session.SessionManager;
import com.libre.im.core.session.WebsocketSession;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

/**
 * @author ZC
 * @date 2021/8/8 0:03
 */
@Slf4j
@RequiredArgsConstructor
public class WebSocketMessageHandler extends SimpleChannelInboundHandler<TextMessageProto.TextMessage> {

    protected final WebsocketServerProperties properties;
    private final SessionManager sessionManager;
    private WebsocketSession session;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextMessageProto.TextMessage msg) throws Exception {
        log.debug("message received: {}", msg);
        sessionManager.put(msg.getSendUserId(), session);
        MessageHandler<?> messageHandler = MessageHandlerFactory.getMessageHandler(msg.getMessageBodyType());
        MessageMapping mapping = MessageMapping.INSTANCE;
        TextMessage textMessage = mapping.sourceToTarget(msg);
        messageHandler.resolveMessage(ctx, textMessage);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        session = new WebsocketSession(ctx);
        log.debug("channel add {}", ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.debug("channel removed: {}", ctx.channel());
        sessionManager.remove(session);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LibreErrorEvent event = new LibreErrorEvent();
        event.setErrorType(ErrorType.WEB_SOCKET);
        ErrorUtil.initErrorInfo(cause, event);
        ApplicationContext context = SpringContext.getContext();
        context.publishEvent(event);
        log.error("error: {}", event);
        ctx.close();
    }

}
