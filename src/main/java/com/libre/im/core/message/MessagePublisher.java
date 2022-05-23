package com.libre.im.core.message;

import com.libre.boot.autoconfigure.SpringContext;
import com.libre.core.exception.LibreException;
import com.libre.im.core.constant.LibreIMConstants;
import com.libre.im.web.pojo.ChatMessage;
import com.libre.redisson.topic.RedissonTopicEventPublisher;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * @author: Libre
 * @Date: 2022/5/23 10:27 PM
 */
@Slf4j
public class MessagePublisher {

	private final static RedissonTopicEventPublisher publisher;

	static {
		publisher = Optional.ofNullable(SpringContext.getBean(RedissonTopicEventPublisher.class))
				.orElseThrow(() -> new LibreException("redissonClient must not be null"));
	}

	public static void publishSaveMessageEvent(ChatMessage message) {
		publisher.publish(LibreIMConstants.MESSAGE_TOPIC, message);
		log.debug("message publish success: {}", message);
	}

}