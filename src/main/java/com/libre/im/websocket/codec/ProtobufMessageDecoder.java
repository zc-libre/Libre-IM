package com.libre.im.websocket.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: Libre
 * @Date: 2022/5/1 7:13 AM
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class ProtobufMessageDecoder extends MessageToMessageDecoder<WebSocketFrame> {

    @Override
    protected void decode(ChannelHandlerContext ctx, WebSocketFrame frame, List<Object> out) throws Exception {
        if (frame instanceof BinaryWebSocketFrame) {
            ByteBuf buf = frame.content();
            out.add(buf);
            buf.retain();
        }
    }
}
