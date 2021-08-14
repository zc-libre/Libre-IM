package com.libre.im.core.listener;

import com.libre.im.core.message.Message;

/**
 * @author ZC
 * @date 2021/8/14 22:48
 */
public interface OfflineMessageListener extends MessageListener {

    /**
     * 发送离线消息
     * @param message /
     */
    void sendOfflineMessage(Message message);
}
