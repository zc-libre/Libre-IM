package com.libre.im.core.handler;

import com.libre.core.toolkit.JSONUtil;
import com.libre.im.core.message.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

/**
 * @author ZC
 * @date 2021/7/31 23:08
 */
@Slf4j
public class TextWebSocketHandler extends AbstractWebsocketHandler<TextWebSocketFrame> {

    @Override
    protected void handleWebSocketFrame(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        Assert.notNull(channelContext, "chatUserContext must not be null");
        log.info(msg.text());
        Message message = JSONUtil.readValue(msg.text(), Message.class);
        log.info(message.getMessage());
    }
}
