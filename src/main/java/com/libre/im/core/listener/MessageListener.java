package com.libre.im.core.listener;

import com.libre.im.core.message.Message;

/**
 * @author ZC
 * @date 2021/8/14 22:33
 */
public interface MessageListener {

    /**
     * 保存聊天记录
     * @param message /
     */
    void saveMessage(Message message);

}
