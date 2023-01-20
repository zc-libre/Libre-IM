package com.libre.im.websocket.core.handler;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.util.URLUtil;
import com.libre.im.security.config.LibreSecurityProperties;
import com.libre.im.security.jwt.JwtTokenStore;
import com.libre.im.security.pojo.vo.TokenVO;
import com.libre.im.websocket.core.constant.LibreIMConstants;
import com.libre.im.websocket.core.exception.LibreImException;
import com.libre.im.websocket.core.session.Session;
import com.libre.im.websocket.core.session.SessionManager;
import com.libre.im.websocket.core.session.WebsocketSession;
import com.libre.toolkit.core.StringUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Clock;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
@ChannelHandler.Sharable
public class WebSocketJwtTokenHandler extends SimpleChannelInboundHandler<FullHttpRequest>
		implements SmartInitializingSingleton {

	@Resource
	private LibreSecurityProperties securityProperties;

	@Resource
	private SessionManager sessionManager;

	@Resource
	private JwtTokenStore jwtTokenStore;

	/**
	 * 此处进行url参数提取，重定向URL，访问webSocket的url不支持带参数的，带参数会抛异常，这里先提取参数，将参数放入通道中传递下去，重新设置一个不带参数的url
	 * @param ctx the {@link ChannelHandlerContext} which this
	 * {@link SimpleChannelInboundHandler} belongs to
	 * @param request the message to handle
	 * @throws Exception
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
		String uri = request.uri();
		log.info("WebSocketJwtTokenHandler.channelRead0 --> : 格式化URL... {}", uri);
		Map<CharSequence, CharSequence> queryMap = UrlBuilder.ofHttp(uri).getQuery().getQueryMap();
		// 将参数放入通道中传递下去
		String header = securityProperties.getJwtToken().getHeader();
		AttributeKey<String> tokenKey = AttributeKey.valueOf(header);
		String token = queryMap.get(header).toString();
		log.info("token: {}", token);
		if (StringUtil.isBlank(token)) {
			throw new LibreImException("请求token为空");
		}
		TokenVO tokenVO = jwtTokenStore.get(token);

		if (Objects.isNull(tokenVO)) {
			throw new LibreImException("token已过期");
		}

		ctx.channel().attr(tokenKey).setIfAbsent(token);
		request.setUri(URLUtil.getPath(uri));

		long sessionId = tokenVO.getUserId();
		Session session;
		if (!sessionManager.isExist(sessionId)) {
			session = WebsocketSession.of(ctx, sessionId);
			sessionManager.put(sessionId, session);
		}
		else {
			session = sessionManager.getSession(sessionId);
		}
		session.addAttribute(LibreIMConstants.SERVER_SESSION_KEY, sessionId);
		ctx.fireChannelRead(request.retain());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		log.error("WebSocketJwtTokenHandler.exceptionCaught --> cause: ", cause);
		ctx.close();
	}

	@Override
	public void afterSingletonsInstantiated() {

	}

}