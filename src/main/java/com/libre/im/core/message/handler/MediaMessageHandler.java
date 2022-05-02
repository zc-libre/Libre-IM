package com.libre.im.core.message.handler;

import com.libre.im.core.message.MediaMessage;
import com.libre.im.core.message.Message;
import com.libre.im.core.message.MessageBodyType;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author ZC
 * @date 2021/8/7 14:07
 */
public class MediaMessageHandler extends AbstractMessageHandler<MediaMessage> {

    @Override
    public MessageBodyType getMessageType() {
        return MessageBodyType.MEDIA;
    }

    @Override
    public MediaMessage newInstance() {
        return new MediaMessage();
    }



    @Override
    protected void sendMessage(ChannelHandlerContext ctx, Message message) {

    }
}
