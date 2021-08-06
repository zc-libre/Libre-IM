package com.libre.im.core.server;

import com.libre.im.core.handler.WebSocketChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;

/**
 * @author ZC
 * @date 2021/7/31 22:54
 */
@Slf4j
public class LibreWebsocketServer {

    public static void run (int port)  {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();

        try {
            bootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(port)
                    .childHandler(new WebSocketChannelInitializer())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            log.info("websocket server init success");

            ChannelFuture channelFuture = bootstrap.bind().sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("websocket server error: {}", NestedExceptionUtils.buildMessage(e.getMessage(), e));
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
