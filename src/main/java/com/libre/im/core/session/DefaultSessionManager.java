package com.libre.im.core.session;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * @author ZC
 * @date 2021/8/8 16:42
 */
@Component
public class DefaultSessionManager implements SessionManager {

    private static final Map<Long, Session> SESSION_MAP = Maps.newConcurrentMap();

    @Override
    public Session get(Long userId) {
        return SESSION_MAP.get(userId);
    }

    @Override
    public void put(Long userId, Session session) {
        if (Objects.nonNull(userId)) {
            SESSION_MAP.put(userId, session);
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
                SESSION_MAP.remove(entry.getKey());
            }
        }
    }

    @Override
    public boolean isExist(Long userId) {
        return SESSION_MAP.containsKey(userId);
    }
}
