package com.libre.im.websocket.core.handler;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.libre.boot.autoconfigure.SpringContext;
import com.libre.boot.exception.ErrorType;
import com.libre.boot.exception.ErrorUtil;
import com.libre.boot.exception.LibreErrorEvent;
import com.libre.im.common.security.dto.AuthUser;
import com.libre.im.common.security.support.SecurityUtil;
import com.libre.im.security.config.LibreSecurityProperties;
import com.libre.im.security.jwt.JwtTokenStore;
import com.libre.im.websocket.config.WebsocketServerProperties;
import com.libre.im.websocket.core.constant.LibreIMConstants;
import com.libre.im.websocket.core.mapstruct.MessageMapping;
import com.libre.im.websocket.core.message.handler.MessageHandler;
import com.libre.im.websocket.core.message.handler.TextMessageHandler;
import com.libre.im.websocket.core.proto.TextMessageProto;
import com.libre.im.websocket.core.message.enums.ConnectType;
import com.libre.im.websocket.core.message.enums.MessageBodyType;
import com.libre.im.websocket.core.message.TextMessage;
import com.libre.im.websocket.core.message.handler.MessageHandlerFactory;
import com.libre.im.websocket.core.session.Session;
import com.libre.im.websocket.core.session.SessionManager;
import com.libre.im.websocket.core.session.WebsocketSession;
import com.libre.toolkit.core.StringUtil;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.time.Clock;
import java.util.Map;

/**
 * @author ZC
 * @date 2021/8/8 0:03
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ChannelHandler.Sharable
public class WebSocketMessageHandler extends SimpleChannelInboundHandler<TextMessageProto.TextMessage> {

	protected final WebsocketServerProperties properties;

	private final SessionManager sessionManager;

	private final ApplicationEventPublisher publisher;

	private final LibreSecurityProperties securityProperties;

	private final JwtTokenStore jwtTokenStore;

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object event) throws Exception {

		if (event instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
			// 协议握手成功完成
			log.info("NettyWebSocketHandler.userEventTriggered --> : 协议握手成功完成");
			// 检查用户token
			AttributeKey<String> attributeKey = AttributeKey.valueOf(securityProperties.getJwtToken().getHeader());
			// 从通道中获取用户token
			String token = ctx.channel().attr(attributeKey).get();
			log.info("token: {}", token);
		}

		Long sessionId = ctx.channel().attr(LibreIMConstants.SERVER_SESSION_ID).get();

		// 发送心跳包
		if (event instanceof IdleStateEvent && ((IdleStateEvent) event).state().equals(IdleState.WRITER_IDLE)) {
			sendHeartBeatMessage(sessionId);
		}

		if (event instanceof IdleStateEvent && ((IdleStateEvent) event).state().equals(IdleState.READER_IDLE)) {
			Long lastTime = ctx.channel().attr(LibreIMConstants.SERVER_SESSION_HEART_BEAT).get();
			if ((Clock.systemDefaultZone().millis() - lastTime) / 1000 >= 60) {
				log.info("与客户端失去连接, 断开连接");
				sessionManager.remove(sessionId);
			}
		}
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextMessageProto.TextMessage msg) throws Exception {
		log.debug("message received: {}", msg);

		if (ConnectType.HEART_BEAT.getType() == msg.getConnectType()) {
			long sessionId = msg.getSendUserId();
			sendHeartBeatMessage(sessionId);
		}
		else {
			MessageHandler<?> messageHandler = MessageHandlerFactory.getMessageHandler(msg.getMessageBodyType());
			MessageMapping mapping = MessageMapping.INSTANCE;
			TextMessage textMessage = mapping.sourceToTarget(msg);
			messageHandler.resolveMessage(textMessage);
		}
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		log.debug("channel add {}", ctx.channel());
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		log.debug("channel removed: {}", ctx.channel());
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		log.debug("channel Inactive: {}", ctx.channel());
		sessionManager.remove(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		LibreErrorEvent event = new LibreErrorEvent();
		event.setErrorType(ErrorType.WEB_SOCKET);
		ErrorUtil.initErrorInfo(cause, event);
		publisher.publishEvent(event);
		log.error("error: {}", event);
		sessionManager.remove(ctx);
		ctx.close();
	}

	private void sendHeartBeatMessage(long sessionId) {
		TextMessageHandler messageHandler = MessageHandlerFactory.getMessageHandler(MessageBodyType.TEXT.getCode());
		messageHandler.sendHeartBeatMessage(sessionId);
	}

}
