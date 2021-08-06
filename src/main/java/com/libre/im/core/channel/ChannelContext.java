package com.libre.im.core.channel;

import com.libre.im.core.pojo.ChatUser;
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
    void addUser(Long userId, Channel channel);

    /**
     * 移除channel
     * @param userId 用户id
     */
    void removeUser(Long userId);

    /**
     * 获取channel
     * @param userId 用户
     * @return channel
     */
    Channel getUser(Long userId);

    /**
     * 删除channel
     * @param channel /
     */
    void removeUserByChannel(Channel channel);
}
