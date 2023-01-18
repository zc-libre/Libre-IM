package com.libre.im.websocket.core.session;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author ZC
 * @date 2021/8/8 16:27
 */
public interface SessionManager {

    /**
     * 获取session
     * @param sessionId sessionId
     * @return {@link Session}
     */
    Session getSession(Long sessionId);

    /**
     * 获取session
     * @param ctx {@link ChannelHandlerContext}
     * @return {@link Session}
     */
    Session getSession(ChannelHandlerContext ctx);

    /**
     * 保存session
     * @param sessionId sessionId
     * @param session {@link Session}
     */
    void put(Long sessionId, Session session);


    /**
     * 移除session
     * @param sessionId sessionId
     */
    void remove(Long sessionId);

    /**
     * 删除Session
     * @param Session {@link Session}
     */
    void remove(Session Session);

    /**
     * 删除Session
     * @param ctx {@link ChannelHandlerContext}
     */
    void remove(ChannelHandlerContext ctx);

    /**
     * 是否存在session
     * @param sessionId sessionId
     * @return boolean
     */
    boolean isExist(Long sessionId);
}
