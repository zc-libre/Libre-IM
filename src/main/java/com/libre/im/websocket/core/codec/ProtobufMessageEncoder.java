package com.libre.im.websocket.core.codec;

import com.google.protobuf.MessageLite;
import com.libre.im.websocket.core.message.handler.MessageHandler;
import com.libre.im.websocket.core.message.Message;
import com.libre.im.websocket.core.message.handler.MessageHandlerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@ChannelHandler.Sharable
public class ProtobufMessageEncoder extends MessageToMessageEncoder<Message> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, List<Object> list) throws Exception {
        MessageHandler<?> messageHandler = MessageHandlerFactory.getMessageHandler(message.getMessageBodyType());
        MessageLite messageLite = messageHandler.getMessage(message);
        ByteBuf result = Unpooled.wrappedBuffer((messageLite).toByteArray());
        WebSocketFrame frame = new BinaryWebSocketFrame(result);
        list.add(frame);
    }
}