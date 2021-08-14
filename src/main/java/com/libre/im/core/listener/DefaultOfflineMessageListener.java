package com.libre.im.core.listener;

import com.libre.im.core.message.Message;
import org.springframework.stereotype.Component;

/**
 * @author ZC
 * @date 2021/8/14 23:04
 */
@Component
public class DefaultOfflineMessageListener implements OfflineMessageListener {

    @Override
    public void saveMessage(Message message) {

    }

    @Override
    public void sendOfflineMessage(Message message) {

    }
}
