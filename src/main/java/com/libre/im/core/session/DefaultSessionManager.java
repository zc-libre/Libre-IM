package com.libre.im.core.session;

import com.google.common.collect.Maps;
import com.libre.core.toolkit.StringUtil;
import com.libre.im.core.channel.ChannelContext;
import com.libre.im.core.constant.LibreIMConstants;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Map;
import java.util.Objects;

/**
 * @author ZC
 * @date 2021/8/8 16:42
 */
@Component
@RequiredArgsConstructor
public class DefaultSessionManager implements SessionManager {

    private final Map<Long, Session> sessionMap = Maps.newConcurrentMap();
    private final ChannelContext channelContext;

    @Override
    public Session getSession(Long sessionId) {
        return sessionMap.get(sessionId);
    }

    @Override
    public Session getSession(ChannelHandlerContext ctx) {
        String sessionIdStr = ctx.channel().attr(LibreIMConstants.SERVER_SESSION_ID).get();
        if (StringUtil.isNotBlank(sessionIdStr)) {
            Long sessionId = Long.parseLong(sessionIdStr);
            return getSession(sessionId);
        }
        return null;
    }

    @Override
    public void put(Long sessionId, Session session) {
        if (Objects.nonNull(sessionId)) {
            sessionMap.put(sessionId, session);
            Channel channel = channelContext.getChannel(sessionId);
            if (Objects.isNull(channel)) {
                channelContext.addChannel(sessionId, session.getChannel());
            }
        }
    }

    @Override
    public void remove(Long sessionId) {
        Session session = getSession(sessionId);
        if (Objects.isNull(session)) {
            return;
        }
        session.close();
        sessionMap.remove(sessionId);
    }

    @Override
    public void remove(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        String sessionIdStr = channel.attr(LibreIMConstants.SERVER_SESSION_ID).get();
        if (StringUtil.isBlank(sessionIdStr)) {
            return;
        }
        long sessionId = Long.parseLong(sessionIdStr);
        remove(sessionId);
    }

    @Override
    public void remove(Session session) {
        boolean exist = isExist(session.getSessionId());
        if (!exist) {
            return;
        }
        for (Map.Entry<Long, Session> entry : sessionMap.entrySet()) {
            if (entry.getValue().equals(session)) {
                sessionMap.remove(entry.getKey());
                session.close();
            }
        }
    }

    @Override
    public boolean isExist(Long sessionId) {
        if (!sessionMap.containsKey(sessionId)) {
           return false;
        }
        Session session = sessionMap.get(sessionId);
        Channel channel = session.getChannel();
        if (!channel.hasAttr(LibreIMConstants.SERVER_SESSION_ID)) {
            return false;
        }
        String id = channel.attr(LibreIMConstants.SERVER_SESSION_ID).get();
        return StringUtil.isNotBlank(id) && ObjectUtils.nullSafeEquals(String.valueOf(sessionId), id);
    }
}
