package com.libre.im.core.codec;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.libre.core.json.JsonUtil;
import com.libre.im.core.message.MediaMessage;
import com.libre.im.core.message.MessageBodyType;
import com.libre.im.core.message.TextMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author: Libre
 * @Date: 2022/4/30 3:21 AM
 */


public class JacksonMessageDecoder extends MessageToMessageDecoder<WebSocketFrame> {

    private final ObjectMapper objectMapper;

    public JacksonMessageDecoder(ObjectMapper objectMapper) {
        Optional.ofNullable(objectMapper).orElseGet(ObjectMapper::new);
        this.objectMapper = objectMapper;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, WebSocketFrame webSocketFrame, List<Object> objects) throws Exception {
        ByteBuf byteBuf = webSocketFrame.content();
        byte[] data = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(data);
        if (webSocketFrame instanceof TextWebSocketFrame) {
            TextMessage textMessage = objectMapper.readValue(data, TextMessage.class);
            objects.add(textMessage);
        } else if (webSocketFrame instanceof BinaryWebSocketFrame) {
            MediaMessage mediaMessage = objectMapper.readValue(data, MediaMessage.class);
            objects.add(mediaMessage);
        }

    }
}