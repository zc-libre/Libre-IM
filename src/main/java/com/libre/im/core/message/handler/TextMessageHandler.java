package com.libre.im.core.message.handler;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.libre.im.core.exception.LibreImException;
import com.libre.im.core.mapstruct.MessageMapping;
import com.libre.im.core.message.*;
import com.libre.im.core.proto.TextMessageProto;
import com.libre.im.core.session.Session;
import com.libre.im.web.pojo.ChatMessage;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * @author ZC
 * @date 2021/8/7 14:06
 */
@Slf4j
public class TextMessageHandler extends AbstractMessageHandler<TextMessage> {

	@Override
	protected void sendMessage(Message message) {
		Long id = Optional.ofNullable(message.getAcceptUserId())
				.orElseThrow(() -> new IllegalArgumentException("acceptUserId not find"));

		TextMessage textMessage = buildTextMessage(message);
		MessageMapping mapping = MessageMapping.INSTANCE;
		ChatMessage chatMessage = mapping.convertToChatMessage(textMessage);

		Session session = sessionManager.getSession(id);
		if (Objects.isNull(session)) {
			chatMessage.setStatus(MessageStatus.OFFLINE.getStatus());
			MessagePublisher.publishSaveMessageEvent(chatMessage);
			return;
		}

		Channel channel = session.getChannel();
		channel.writeAndFlush(textMessage);
		MessagePublisher.publishSaveMessageEvent(chatMessage);
	}

	@Override
	public MessageBodyType getMessageType() {
		return MessageBodyType.TEXT;
	}

	@Override
	public TextMessageProto.TextMessage getMessage(Message message) {
		if (ObjectUtils.nullSafeEquals(getMessageType().getCode(), message.getMessageBodyType())) {
			TextMessage textMessage = (TextMessage) message;
			MessageMapping mapping = MessageMapping.INSTANCE;
			return mapping.targetToSource(textMessage);
		}
		throw new LibreImException("messageBodyType match failed");
	}

	@Override
	public TextMessage newInstance() {
		return new TextMessage();
	}

	public TextMessage buildTextMessage(Message message) {
		TextMessage textMessage = new TextMessage();
		textMessage.setId(IdWorker.getId());
		textMessage.setCreateTime(LocalDateTime.now());
		textMessage.setBody(message.getBody());
		textMessage.setAcceptUserId(message.getAcceptUserId());
		textMessage.setMessageBodyType(MessageBodyType.TEXT.getCode());
		textMessage.setSendUserId(message.getSendUserId());
		textMessage.setConnectType(ConnectType.SEND.getType());
		return textMessage;
	}

}
