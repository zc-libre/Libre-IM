package com.libre.im.core.channel;

import com.libre.im.core.pojo.ChatUser;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ZC
 * @date 2021/8/1 14:02
 */
@Component
public class DefaultChannelContext implements ChannelContext {

    private static final Map<Long, Channel> CHANNEL_CONTEXT;

     static {
         CHANNEL_CONTEXT = new ConcurrentHashMap<>(1024);
     }

    @Override
    public void addChannel(Long userId, Channel channel) {
        CHANNEL_CONTEXT.put(userId, channel);
    }

    @Override
    public void removeChannel(Long userId) {
        CHANNEL_CONTEXT.remove(userId);
    }

    @Override
    public Channel getChannel(Long userId) {
        return CHANNEL_CONTEXT.get(userId);
    }

    @Override
    public void removeUserByChannel(Channel channel) {
        if (Objects.isNull(channel)) {
            return;
        }
        for (Map.Entry<Long, Channel> entry : CHANNEL_CONTEXT.entrySet()) {
            if (channel.equals(entry.getValue())) {
                CHANNEL_CONTEXT.remove(entry.getKey());
            }
        }
    }
}
