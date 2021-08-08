package com.libre.im.core.session;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author ZC
 * @date 2021/8/8 16:42
 */
@Component
public class DefaultSessionManager implements SessionManager {

    private static final Map<String, Session> SESSION_MAP = Maps.newConcurrentMap();

    @Override
    public Session get(String key) {
        return SESSION_MAP.get(key);
    }

    @Override
    public void put(Session session) {
        String key = session.getKey();
        if (StrUtil.isNotEmpty(key)) {
            SESSION_MAP.put(key, session);
        }
    }

    @Override
    public void remove(String key) {
        SESSION_MAP.remove(key);
    }

    @Override
    public void remove(Session session) {
         SESSION_MAP.remove(session.getKey());
    }

    @Override
    public boolean isExist(String key) {
        return SESSION_MAP.containsKey(key);
    }
}
