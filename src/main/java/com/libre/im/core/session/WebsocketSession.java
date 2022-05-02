package com.libre.im.core.session;

import com.google.common.collect.Maps;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.util.Map;

/**
 * @author ZC
 * @date 2021/8/7 22:32
 */

public class WebsocketSession extends AbstractSession {

    private final Map<String, Object> attribute = Maps.newConcurrentMap();

    public WebsocketSession(ChannelHandlerContext ctx) {
        super(ctx);
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public Channel getChannel() {
        return this.ctx.channel();
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

    }

    @Override
    public <T> T getAttribute(String key) {
        return null;
    }
}
