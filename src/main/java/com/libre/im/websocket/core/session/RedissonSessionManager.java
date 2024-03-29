package com.libre.im.websocket.core.session;

import lombok.RequiredArgsConstructor;
import org.redisson.api.*;

/**
 * @author: Libre
 * @Date: 2022/5/5 2:30 AM
 */

@RequiredArgsConstructor
public class RedissonSessionManager extends AbstractSessionManager {

    private final RedissonClient redissonClient;
    public final static String SESSION_KEY = "libre:im:session:";

    @Override
    public Session getSession(Long sessionId) {
        RBucket<Session> bucket = getSessionBucket(sessionId);
        return bucket.get();
    }

    @Override
    public void put(Long sessionId, Session session) {
        RBucket<Session> bucket = getSessionBucket(sessionId);
        bucket.set(session);
    }

    @Override
    public void remove(Long sessionId) {
        RBucket<Session> bucket = getSessionBucket(sessionId);
        bucket.deleteAsync();
    }


    @Override
    public void remove(Session Session) {
        Long sessionId = Session.getSessionId();
        remove(sessionId);
    }


    @Override
    public boolean isExist(Long sessionId) {
         RBucket<Session> bucket = getSessionBucket(sessionId);
         return bucket.isExists();
    }

    private RBucket<Session> getSessionBucket(Long sessionId) {
        return redissonClient.getBucket(getSessionKey(sessionId));
    }

    private String getSessionKey(Long sessionId) {
        return SESSION_KEY + sessionId;
    }
}
