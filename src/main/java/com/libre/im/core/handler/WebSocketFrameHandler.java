package com.libre.im.core.handler;

import com.libre.core.toolkit.SpringUtils;
import com.libre.im.core.channel.ChannelContext;
import com.libre.im.core.channel.WebSocketChannelGroup;
import com.libre.im.core.config.WebsocketServerProperties;
import com.libre.im.core.message.Message;
import com.libre.im.core.message.MessageFactory;
import com.libre.im.core.message.MessageType;
import com.libre.im.core.message.handler.MessageHandler;
import com.libre.im.core.message.handler.MessageHandlerFactory;
import com.libre.im.core.pojo.ChatUser;
import com.libre.im.core.session.DefaultSessionManager;
import com.libre.im.core.session.SessionManager;
import com.libre.im.core.session.WebsocketSession;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.undertow.websockets.core.WebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * @author ZC
 * @date 2021/8/8 0:03
 */
@Slf4j
public class WebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    protected final WebsocketServerProperties properties;
    protected final ChannelContext channelContext;
    private final MessageHandlerFactory messageHandlerFactory;
    private final SessionManager sessionManager;
    private WebsocketSession session;

    public WebSocketFrameHandler() {
        properties = SpringUtils.getBean(WebsocketServerProperties.class);
        channelContext = SpringUtils.getBean(ChannelContext.class);
        messageHandlerFactory = SpringUtils.getBean(MessageHandlerFactory.class);
        sessionManager = SpringUtils.getBean(SessionManager.class);
        Assert.notNull(channelContext, "chatUserContext must not be null");
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        session = new WebsocketSession(ctx);
        sessionManager.put(session);
        log.debug("channel add {}",ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.debug("channel removed: {}", ctx.channel());
        channelContext.removeUserByChannel(ctx.channel());
        session.close();
    }


    @Override
    public void channelRead0(ChannelHandlerContext ctx, WebSocketFrame webSocketFrame) throws Exception {
        Assert.notNull(channelContext, "chatUserContext must not be null");

        if (webSocketFrame instanceof TextWebSocketFrame) {

            TextWebSocketFrame msg = (TextWebSocketFrame) webSocketFrame;
            log.info("message received: {}",msg.text());

            Message message = MessageFactory.getMessage(msg.text());

            if (Objects.nonNull(message)) {
                ChatUser from = message.getFrom();
                Long fromUserId = from.getFromUserId();
                channelContext.addChannel(fromUserId, ctx.channel());
                Integer type = message.getType();
                MessageHandler handler = messageHandlerFactory.getHandler(MessageType.getType(type));
                handler.sendMessage(ctx, message);
            }
        }
    }

    /**
     * 功能：读空闲时移除Channel
     * @param ctx /
     * @param evt /
     * @throws Exception /
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            // 判断Channel是否读空闲, 读空闲时移除Channel
            if (event.state().equals(IdleState.READER_IDLE)) {
                channelContext.removeUserByChannel(ctx.channel());
            }
        }
        ctx.fireUserEventTriggered(evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // TODO 日志记录
        ctx.close();
    }
/*
    *//**
     * 处理http请求
     * @param ctx /
     * @param req /
     *//*
    protected  void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req){
        if (req != null) {
            HttpMethod method = req.method();
            // 如果是websocket请求就握手升级
            if (properties.getWsUri().equalsIgnoreCase(req.uri())) {
                log.debug(" req instanceof HttpRequest");
                WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                        properties.getWsFactoryUri(), null, false);
                handshaker = wsFactory.newHandshaker(req);
                if (handshaker == null) {
                    WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
                } else {
                    handshaker.handshake(ctx.channel(), req);
                }
            }
        }
    }*/
}
