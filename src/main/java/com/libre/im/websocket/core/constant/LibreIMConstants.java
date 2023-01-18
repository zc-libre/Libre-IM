package com.libre.im.websocket.core.constant;

import io.netty.util.AttributeKey;

/**
 * @author: Libre
 * @Date: 2022/4/30 2:52 AM
 */
public class LibreIMConstants {

	public final static String SERVER_SESSION_KEY = "SERVER_SESSION_ID";

	public final static String SERVER_SESSION_HEART_BEAT_KEY = "SERVER_SESSION_HEART_BEAT";

	public final static String PING = "PING";

	public final static String PONG = "PONG";

	public final static AttributeKey<String> SERVER_SESSION_ID = AttributeKey.valueOf(SERVER_SESSION_KEY);

	public static final AttributeKey<String> SERVER_SESSION_HEART_BEAT = AttributeKey
			.valueOf(SERVER_SESSION_HEART_BEAT_KEY);

	public final static String MESSAGE_TOPIC = "libre:im:message:topic";

}
