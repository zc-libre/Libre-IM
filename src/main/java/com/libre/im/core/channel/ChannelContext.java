package com.libre.im.core.channel;

import io.netty.channel.Channel;

/**
 * @author ZC
 * @date 2021/8/1 14:23
 */
public interface ChannelContext {

    /**
     * 加入channel
     * @param userId 用户
     * @param channel channel
     */
    void addChannel(Long userId, Channel channel);

    /**
     * 移除channel
     * @param userId 用户id
     */
    void removeChannel(Long userId);

    /**
     * 获取channel
     * @param userId 用户
     * @return channel
     */
    Channel getChannel(Long userId);

    /**
     * 删除channel
     * @param channel /
     */
    void removeUserByChannel(Channel channel);
}
