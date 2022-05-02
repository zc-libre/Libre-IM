package com.libre.im.core.session;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import java.net.InetSocketAddress;

/**
 * @author ZC
 * @date 2021/8/7 22:26
 */
public abstract class AbstractSession implements Session {

    protected final ChannelHandlerContext ctx;
    protected final Long id;

    public AbstractSession(ChannelHandlerContext ctx) {
        this.ctx = ctx;
        this.id = IdWorker.getId();
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
