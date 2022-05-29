package com.libre.im.websocket.session;

import com.libre.im.websocket.constant.LibreIMConstants;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import org.springframework.util.Assert;

import java.net.InetSocketAddress;

/**
 * @author ZC
 * @date 2021/8/7 22:26
 */
public abstract class AbstractSession implements Session {

    protected final ChannelHandlerContext ctx;
    protected final Long sessionId;
    protected final Channel channel;

    protected AbstractSession(ChannelHandlerContext ctx, Long sessionId) {
        Assert.notNull(ctx, "ChannelHandlerContext must not be null");
        this.ctx = ctx;
        Assert.notNull(sessionId, "sessionId must not be null");
        this.sessionId = sessionId;
        this.channel = ctx.channel();
        ctx.channel().attr(LibreIMConstants.SERVER_SESSION_ID).set(String.valueOf(sessionId));
    }

    @Override
    public String getRemoteAddress() {
        InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        return socketAddress.getAddress().toString();
    }

    @Override
    public void close() {
        ChannelFuture cf = ctx.writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FORBIDDEN));
        cf.addListener(ChannelFutureListener.CLOSE);
    }
}
