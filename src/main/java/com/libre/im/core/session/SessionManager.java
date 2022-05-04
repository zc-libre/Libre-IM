package com.libre.im.core.session;

import com.libre.im.core.channel.ChannelContext;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author ZC
 * @date 2021/8/8 16:27
 */
public interface SessionManager {

    /**
     * 获取session
     * @param sessionId /
     * @return /
     */
    Session getSession(Long sessionId);

    Session getSession(ChannelHandlerContext ctx);

    /**
     * 保存session
     * @param sessionId /
     * @param Session /
     */
    void put(Long sessionId, Session Session);


    /**
     * 移除session
     * @param sessionId /
     */
    void remove(Long sessionId);

    /**
     * 删除
     * @param Session /
     */
    void remove(Session Session);


    void remove(ChannelHandlerContext ctx);

    /**
     * 是否存在session
     * @param sessionId /
     * @return /
     */
    boolean isExist(Long sessionId);
}
