package com.libre.im.websocket.core.message;

import com.libre.im.websocket.core.constant.LibreIMConstants;
import com.libre.im.websocket.core.message.enums.ConnectType;
import com.libre.im.websocket.core.message.enums.MessageBodyType;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

/**
 * @author: Libre
 * @Date: 2022/5/23 11:47 PM
 */
@UtilityClass
public class MessageFactory {

	public static TextMessage buildHeartBeatMessage() {
		TextMessage heartBeatMessage = new TextMessage();
		heartBeatMessage.setCreateTime(LocalDateTime.now());
		heartBeatMessage.setBody(LibreIMConstants.PONG);
		heartBeatMessage.setMessageBodyType(MessageBodyType.TEXT.getCode());
		heartBeatMessage.setConnectType(ConnectType.SEND.getType());
		heartBeatMessage.setCreateTime(LocalDateTime.now());
		return heartBeatMessage;
	}

}
