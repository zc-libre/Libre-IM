package com.libre.im.core.server;

import com.libre.im.core.codec.ProtobufMessageDecoder;
import com.libre.im.core.codec.ProtobufMessageEncoder;
import com.libre.im.core.config.WebsocketServerProperties;
import com.libre.im.core.handler.WebSocketChannelInitializer;
import com.libre.im.core.handler.WebSocketMessageHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.stereotype.Component;

/**
 * @author ZC
 * @date 2021/7/31 22:54
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LibreWebsocketServer {

    private final ProtobufMessageDecoder protobufMessageDecoder;
    private final ProtobufMessageEncoder protobufMessageEncoder;
    private final WebsocketServerProperties properties;
    private final WebSocketMessageHandler webSocketMessageHandler;

    public void run() {
        Integer port = properties.getPort();
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();

        try {
            bootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(port)
                    .childHandler(new WebSocketChannelInitializer(protobufMessageEncoder, protobufMessageDecoder, properties, webSocketMessageHandler))
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture channelFuture = bootstrap.bind().addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    log.info("websocket server started on {}", port);
                } else {
                    log.error("Cannot start server, follows exception: {}", future.cause().getMessage());
                }
            });
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("websocket server error: {}", NestedExceptionUtils.buildMessage(e.getMessage(), e));
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
