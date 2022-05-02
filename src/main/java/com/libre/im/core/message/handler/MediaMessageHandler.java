package com.libre.im.core.message.handler;

import com.libre.im.core.exception.LibreImException;
import com.libre.im.core.message.MediaMessage;
import com.libre.im.core.message.Message;
import com.libre.im.core.message.MessageBodyType;
import com.libre.im.core.proto.TextMessageProto;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.util.ObjectUtils;

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
    public TextMessageProto.TextMessage getMessage(Message message) {
        if (ObjectUtils.nullSafeEquals(getMessageType().getCode(), message.getMessageBodyType())) {
            MediaMessage mediaMessage = (MediaMessage) message;
        }
        throw new LibreImException("messageBodyType match failed");
    }

    @Override
    protected void sendMessage(ChannelHandlerContext ctx, Message message) {

    }
}
