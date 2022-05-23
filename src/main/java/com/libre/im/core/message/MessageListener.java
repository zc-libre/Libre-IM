package com.libre.im.core.message;

import com.libre.im.core.constant.LibreIMConstants;
import com.libre.im.web.pojo.ChatMessage;
import com.libre.im.web.service.ChatMessageService;
import com.libre.redisson.topic.RTopicEvent;
import com.libre.redisson.topic.RTopicListener;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author: Libre
 * @Date: 2022/5/23 10:40 PM
 */
@Component
@RequiredArgsConstructor
public class MessageListener {

    private final ChatMessageService messageService;

    @Async
    @RTopicListener(LibreIMConstants.MESSAGE_TOPIC)
    public void onSaveMessage(RTopicEvent<ChatMessage> event) {
        ChatMessage message = event.getMsg();
        messageService.saveOrUpdate(message);
    }
}
