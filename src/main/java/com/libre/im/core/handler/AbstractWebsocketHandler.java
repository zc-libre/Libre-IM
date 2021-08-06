package com.libre.im.core.handler;

import com.libre.core.toolkit.SpringUtils;
import com.libre.im.core.channel.ChannelContext;
import com.libre.im.core.channel.WebSocketChannelGroup;
import com.libre.im.core.config.WebsocketServerProperties;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

/**
 * @author ZC
 * @date 2021/8/1 13:36
 */
@Slf4j
public abstract class AbstractWebsocketHandler<T extends WebSocketFrame> extends SimpleChannelInboundHandler<Object> {

    protected final ChannelGroup channelGroup = WebSocketChannelGroup.getInstance();
    protected WebSocketServerHandshaker handshaker;
    protected final WebsocketServerProperties properties;
    protected final ChannelContext channelContext;


    public AbstractWebsocketHandler() {
        properties = SpringUtils.getBean(WebsocketServerProperties.class);
        channelContext = SpringUtils.getBean(ChannelContext.class);
        Assert.notNull(channelContext, "chatUserContext must not be null");
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("channel add {}",ctx.channel());
        channelGroup.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        channelGroup.remove(ctx.channel());
    }


    @Override
    @SuppressWarnings("unchecked")
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 如果是HTTP请求，进行HTTP操作

        if (msg instanceof FullHttpRequest) {
            handleHttpRequest(ctx, (FullHttpRequest) msg);
            // 如果是Websocket请求，则进行websocket操作
        } else if (msg instanceof WebSocketFrame) {
            System.err.println(msg);
            handleWebSocketFrame(ctx, (T) msg);
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

    /**
     * 处理http请求
     * @param ctx /
     * @param req /
     */
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
    }

    /**
     * 处理websocket请求
     * @param ctx ctx
     * @param msg 消息
     */
    protected abstract void handleWebSocketFrame(ChannelHandlerContext ctx, T msg);
}
