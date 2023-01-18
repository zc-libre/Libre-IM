package com.libre.im.websocket.core.session;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

/**
 * @author ZC
 * @date 2021/8/7 22:32
 */

public class WebsocketSession extends AbstractSession {

    private WebsocketSession(ChannelHandlerContext ctx, Long sessionId) {
        super(ctx, sessionId);
    }

    public static WebsocketSession of(ChannelHandlerContext ctx, Long sessionId) {
        return new WebsocketSession(ctx, sessionId);
    }

    @Override
    public Long getSessionId() {
        return this.sessionId;
    }

    @Override
    public Channel getChannel() {
        return channel;
    }

    @Override
    public void setKey(String key) {

    }

    @Override
    public String getKey() {
        return null;
    }

    @Override
    public void addAttribute(String key, Object value) {
        channel.attr(AttributeKey.valueOf(key)).set(value);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String key) {
      return (T) channel.attr(AttributeKey.valueOf(key)).get();
    }
}
