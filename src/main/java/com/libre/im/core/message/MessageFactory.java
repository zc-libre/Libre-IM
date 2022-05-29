package com.libre.im.core.message;

import com.libre.im.core.constant.LibreIMConstants;
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
