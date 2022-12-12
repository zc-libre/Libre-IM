package com.libre.im.websocket.message.handler;

import com.libre.im.websocket.mapstruct.MessageMapping;
import com.libre.im.websocket.message.*;
import com.libre.im.websocket.message.enums.ConnectType;
import com.libre.im.websocket.message.enums.MessageStatus;
import com.libre.im.websocket.session.Session;
import com.libre.im.websocket.session.SessionManager;
import com.libre.im.web.pojo.ChatMessage;
import io.netty.channel.Channel;

import java.util.Objects;

/**
 * @author: Libre
 * @Date: 2022/4/30 10:54 PM
 */
public abstract class AbstractMessageHandler<T extends Message> implements MessageHandler<T> {

	protected final SessionManager sessionManager;

	protected Class<T> clazz;

	protected AbstractMessageHandler(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	@Override
	public void resolveMessage(Message message) {
		Integer connectType = message.getConnectType();
		if (Objects.equals(connectType, ConnectType.SEND.getType())) {
			sendMessage(message);
		}
		else if (Objects.equals(connectType, ConnectType.RECEIVED.getType())) {
			MessageMapping mapping = MessageMapping.INSTANCE;
			if (message instanceof TextMessage) {
				TextMessage textMessage = (TextMessage) message;
				ChatMessage chatMessage = mapping.convertToChatMessage(textMessage);
				MessagePublisher.publishSaveMessageEvent(chatMessage);
			}
			else if (message instanceof MediaMessage) {
				MediaMessage mediaMessage = (MediaMessage) message;
			}
		}
	}

	public void sendHeartBeatMessage(Long sessionId) {
		TextMessage heartBeatMessage = MessageFactory.buildHeartBeatMessage();
		Session session = sessionManager.getSession(sessionId);
		if (Objects.nonNull(session)) {
			Channel channel = session.getChannel();
			channel.writeAndFlush(heartBeatMessage);
		}
	}

	protected abstract void sendMessage(Message message);

}
