package com.libre.im.core.codec;

import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageLiteOrBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.springframework.stereotype.Component;

import java.util.List;

import static io.netty.buffer.Unpooled.wrappedBuffer;

@Component
@ChannelHandler.Sharable
public class ProtobufMessageEncoder extends MessageToMessageEncoder<MessageLiteOrBuilder> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MessageLiteOrBuilder messageLiteOrBuilder, List<Object> list) throws Exception {
        ByteBuf result = null;
        if (messageLiteOrBuilder instanceof MessageLite) {
            result = wrappedBuffer(((MessageLite) messageLiteOrBuilder).toByteArray());
        }
        if (messageLiteOrBuilder instanceof MessageLite.Builder) {
            result = wrappedBuffer(((MessageLite.Builder) messageLiteOrBuilder).build().toByteArray());
        }
        // 然后下面再转成websocket二进制流，因为客户端不能直接解析protobuf编码生成的
        WebSocketFrame frame = new BinaryWebSocketFrame(result);
        list.add(frame);
    }
}