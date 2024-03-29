package com.libre.im.websocket.core.session;

import com.libre.im.websocket.core.constant.LibreIMConstants;
import com.libre.toolkit.core.StringUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author: Libre
 * @Date: 2022/5/5 2:30 AM
 */
public abstract class AbstractSessionManager implements SessionManager {

    @Override
    public Session getSession(ChannelHandlerContext ctx) {
        Long sessionId = ctx.channel().attr(LibreIMConstants.SERVER_SESSION_ID).get();
        return getSession(sessionId);
    }


    @Override
    public void remove(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        Long sessionId = channel.attr(LibreIMConstants.SERVER_SESSION_ID).get();
        remove(sessionId);
    }

}
