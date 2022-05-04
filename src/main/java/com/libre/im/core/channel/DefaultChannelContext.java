package com.libre.im.core.channel;

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

    private  final Map<Long, Channel> channelContext = new ConcurrentHashMap<>(1024);;

    @Override
    public void addChannel(Long sessionId, Channel channel) {
        channelContext.computeIfAbsent(sessionId, (key) -> channel);
    }

    @Override
    public void removeChannel(Long sessionId) {
        channelContext.remove(sessionId);
    }

    @Override
    public Channel getChannel(Long sessionId) {
        return channelContext.get(sessionId);
    }

    @Override
    public void removeUserByChannel(Channel channel) {
        if (Objects.isNull(channel)) {
            return;
        }
        for (Map.Entry<Long, Channel> entry : channelContext.entrySet()) {
            if (channel.equals(entry.getValue())) {
                channelContext.remove(entry.getKey());
            }
        }
    }
}
