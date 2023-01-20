package com.libre.im.websocket.core.handler;

import com.libre.im.websocket.core.codec.ProtobufMessageDecoder;
import com.libre.im.websocket.core.codec.ProtobufMessageEncoder;
import com.libre.im.websocket.config.WebsocketServerProperties;
import com.libre.im.websocket.core.proto.TextMessageProto;
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

	private final WebSocketJwtTokenHandler webSocketParamHandler;


	@Override
	protected void initChannel(NioSocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
		pipeline.addLast(new HttpServerCodec());
		pipeline.addLast(new HttpObjectAggregator(65536));
		pipeline.addLast(new ChunkedWriteHandler());
		pipeline.addLast(new WebSocketServerCompressionHandler());
		pipeline.addLast(webSocketParamHandler);
		pipeline.addLast(new WebSocketServerHandler(properties.getWsUri()));
		// 解码器，通过Google Protocol Buffers序列化框架动态的切割接收到的ByteBuf
		pipeline.addLast(new ProtobufVarint32FrameDecoder());
		// Google Protocol Buffers 长度属性编码器
		pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
		pipeline.addLast(protobufMessageDecoder);
		pipeline.addLast(protobufMessageEncoder);
		pipeline.addLast(new ProtobufDecoder(TextMessageProto.TextMessage.getDefaultInstance()));
		pipeline.addLast(new IdleStateHandler(properties.getReadIdleTimeOut(), properties.getWriteIdleTimeOut(),
				properties.getAllIdleTimeOut(), TimeUnit.SECONDS));
		pipeline.addLast(webSocketMessageHandler);
	}

}
