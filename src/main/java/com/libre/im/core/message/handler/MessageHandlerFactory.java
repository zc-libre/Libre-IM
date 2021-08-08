package com.libre.im.core.message.handler;

import com.google.common.collect.Maps;
import com.libre.im.core.message.MessageType;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author ZC
 * @date 2021/8/7 14:36
 */
@Component
public class MessageHandlerFactory implements ApplicationContextAware, InitializingBean {

    private final static Map<MessageType, MessageHandler> MESSAGE_HANDLER_MAP = Maps.newEnumMap(MessageType.class);
    private ApplicationContext applicationContext;

    public MessageHandler getHandler(MessageType messageType) {
         return MESSAGE_HANDLER_MAP.get(messageType);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        applicationContext.getBeansOfType(MessageHandler.class)
                .values()
                .forEach(handler -> MESSAGE_HANDLER_MAP.put(handler.getMessageType(), handler));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
       this.applicationContext = applicationContext;
    }
}
