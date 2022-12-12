package com.libre.im.websocket.session;

import com.google.common.cache.Cache;
import com.libre.im.websocket.constant.LibreIMConstants;
import com.libre.toolkit.core.StringUtil;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

/**
 * @author ZC
 * @date 2021/8/8 16:42
 */
@RequiredArgsConstructor
public class DefaultSessionManager extends AbstractSessionManager {

    private final Cache<Long, Session> cache;

    @Override
    public Session getSession(Long sessionId) {
        return cache.getIfPresent(sessionId);
    }

    @Override
    public void put(Long sessionId, Session session) {
        if (Objects.nonNull(sessionId)) {
            cache.put(sessionId, session);
        }
    }

    @Override
    public void remove(Long sessionId) {
        Session session = getSession(sessionId);
        if (Objects.isNull(session)) {
            return;
        }
        session.close();
        cache.invalidate(sessionId);
    }


    @Override
    public void remove(Session session) {
        Long sessionId = session.getSessionId();
        remove(sessionId);
    }

    @Override
    public boolean isExist(Long sessionId) {
        Session session = cache.getIfPresent(sessionId);
        if (Objects.isNull(session)) {
            return false;
        }
        Channel channel = session.getChannel();
        if (!channel.hasAttr(LibreIMConstants.SERVER_SESSION_ID)) {
            return false;
        }
        String id = channel.attr(LibreIMConstants.SERVER_SESSION_ID).get();
        return StringUtil.isNotBlank(id) && ObjectUtils.nullSafeEquals(String.valueOf(sessionId), id);
    }
}
