package com.libre.im.core.channel;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.experimental.UtilityClass;

/**
 * @author ZC
 * @date 2021/8/1 13:09
 */
@UtilityClass
public class WebSocketChannelGroup {

    private static volatile ChannelGroup INSTANCE;

    public static ChannelGroup getInstance() {
        if (INSTANCE == null) {
            synchronized (WebSocketChannelGroup.class) {
               if (INSTANCE == null) {
                   INSTANCE = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
               }
            }
        }
        return INSTANCE;
    }
}
