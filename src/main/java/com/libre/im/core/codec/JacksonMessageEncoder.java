package com.libre.im.core.codec;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libre.im.core.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static io.netty.buffer.Unpooled.wrappedBuffer;

/**
 * @author: Libre
 * @Date: 2022/5/1 12:45 AM
 */


public class JacksonMessageEncoder extends MessageToMessageEncoder<Message> {

    private final ObjectMapper objectMapper;

    public JacksonMessageEncoder(ObjectMapper objectMapper) {
        Optional.ofNullable(objectMapper).orElseGet(ObjectMapper::new);
        this.objectMapper = objectMapper;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, List<Object> list) throws Exception {
        if (Objects.isNull(message)) {
            return;
        }
        ByteBuf buf = wrappedBuffer(objectMapper.writeValueAsBytes(message));
        TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(buf);
        list.add(textWebSocketFrame);
    }
}
