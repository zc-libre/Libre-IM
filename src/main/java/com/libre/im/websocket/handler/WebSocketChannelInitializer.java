package com.libre.im.websocket.handler;

import com.libre.im.websocket.codec.ProtobufMessageDecoder;
import com.libre.im.websocket.codec.ProtobufMessageEncoder;
import com.libre.im.config.WebsocketServerProperties;
import com.libre.im.websocket.proto.TextMessageProto;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.TimeUnit;

/**
 * @author ZC
 * @date 2021/8/6 22:53
 */
@RequiredArgsConstructor
public class WebSocketChannelInitializer extends ChannelInitializer<NioSocketChannel> {

    private final ProtobufMessageEncoder protobufMessageEncoder;
    private final ProtobufMessageDecoder protobufMessageDecoder;
    private final WebsocketServerProperties properties;
    private final WebSocketMessageHandler webSocketMessageHandler;
    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(65536));
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new WebSocketServerCompressionHandler());
        pipeline.addLast(new WebSocketServerProtocolHandler(properties.getWsUri(), null, true, 10 * 1024));
        //??????????????????Google Protocol Buffers??????????????????????????????????????????ByteBuf
        pipeline.addLast(new ProtobufVarint32FrameDecoder());
        //Google Protocol Buffers ?????????????????????
        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
        pipeline.addLast(protobufMessageDecoder);
        pipeline.addLast(protobufMessageEncoder);
        pipeline.addLast(new ProtobufDecoder(TextMessageProto.TextMessage.getDefaultInstance()));
        pipeline.addLast(new IdleStateHandler(properties.getReadIdleTimeOut(), properties.getWriteIdleTimeOut(), properties.getAllIdleTimeOut(), TimeUnit.SECONDS));
        pipeline.addLast(webSocketMessageHandler);
    }
}
