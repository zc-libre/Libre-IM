package com.libre.im.core.codec;

import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageLiteOrBuilder;
import com.libre.im.core.mapstruct.MessageMapping;
import com.libre.im.core.message.Message;
import com.libre.im.core.message.handler.MessageHandler;
import com.libre.im.core.message.handler.MessageHandlerFactory;
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
public class ProtobufMessageEncoder extends MessageToMessageEncoder<Message> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, List<Object> list) throws Exception {
        MessageHandler<?> messageHandler = MessageHandlerFactory.getMessageHandler(message.getMessageBodyType());
        MessageLite messageLite = messageHandler.getMessage(message);
        ByteBuf result = wrappedBuffer((messageLite).toByteArray());
        WebSocketFrame frame = new BinaryWebSocketFrame(result);
        list.add(frame);
    }
}