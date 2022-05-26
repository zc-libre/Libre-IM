package com.libre.im.core.message.handler;

import com.google.common.collect.Maps;
import com.libre.boot.autoconfigure.SpringContext;
import com.libre.im.core.exception.LibreImException;
import com.libre.im.core.message.MessageBodyType;
import com.libre.im.core.session.SessionManager;
import lombok.experimental.UtilityClass;
import org.springframework.context.ApplicationContext;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * @author ZC
 * @date 2021/8/7 14:36
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@UtilityClass
public class MessageHandlerFactory {

	private final static Map<Integer, MessageHandler> messageHandlerContext = Maps.newHashMap();

	static {
		ApplicationContext applicationContext = Optional.ofNullable(SpringContext.getContext())
				.orElseThrow(() -> new LibreImException("applicationContext must not be null"));
		Map<String, MessageHandler> map = applicationContext.getBeansOfType(MessageHandler.class);
		for (MessageHandler handler : map.values()) {
			Integer code = handler.getMessageType().getCode();
			messageHandlerContext.put(code, handler);
		}
	}

	public static <T extends MessageHandler<?>> T getMessageHandler(Integer type) {
		return (T) messageHandlerContext.get(type);
	}

}
