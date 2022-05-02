package com.libre.im.core.session;

import com.google.common.collect.Maps;
import com.libre.im.core.channel.ChannelContext;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author ZC
 * @date 2021/8/8 16:42
 */
@Component
@RequiredArgsConstructor
public class DefaultSessionManager implements SessionManager {

    private static final Map<Long, Session> SESSION_MAP = Maps.newConcurrentMap();
    private final ChannelContext channelContext;

    @Override
    public Session get(Long userId) {
        return SESSION_MAP.get(userId);
    }

    @Override
    public void put(Long userId, Session session) {
        if (Objects.nonNull(userId)) {
            SESSION_MAP.put(userId, session);
            channelContext.addChannel(userId, session.getChannel());
        }
    }

    @Override
    public void remove(Long userId) {
        SESSION_MAP.remove(userId);
    }

    @Override
    public void remove(Session session) {
        for (Map.Entry<Long, Session> entry : SESSION_MAP.entrySet()) {
            if (entry.getValue().equals(session)) {
                session.close();
                SESSION_MAP.remove(entry.getKey());
                Channel channel = channelContext.getChannel(session.getId());
                Optional.ofNullable(channel).ifPresent(channelContext::removeUserByChannel);
            }
        }
    }

    @Override
    public boolean isExist(Long userId) {
        return SESSION_MAP.containsKey(userId);
    }
}
