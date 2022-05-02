package com.libre.im.core.config;

import com.libre.boot.autoconfigure.SpringContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ZC
 * @date 2021/8/1 13:07
 */
@EnableConfigurationProperties(WebsocketServerProperties.class)
@Configuration(proxyBeanMethods = false)
public class WebSocketServerConfiguration {

    @Bean
    public SpringContext springContext() {
        return new SpringContext();
    }

    @Bean
    public ChannelGroup channelGroup() {
        return new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    }
}
