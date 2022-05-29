package com.libre.im.websocket.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.libre.boot.autoconfigure.SpringContext;
import com.libre.im.websocket.session.DefaultSessionManager;
import com.libre.im.websocket.session.RedissonSessionManager;
import com.libre.im.websocket.session.Session;
import com.libre.im.websocket.session.SessionManager;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

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

    @Bean
    @ConditionalOnBean(RedissonClient.class)
    public SessionManager redissonSessionManager(RedissonClient redissonClient) {
        return new RedissonSessionManager(redissonClient);
    }

    @Bean
    @ConditionalOnMissingBean(RedissonClient.class)
    public SessionManager defaultSessionManager() {
        CacheBuilder<Object, Object> sessionCacheBuilder = CacheBuilder.newBuilder();
        Cache<Long, Session> sessionCache = sessionCacheBuilder
                .expireAfterAccess(Duration.ofMinutes(30))
                .build();
        return new DefaultSessionManager(sessionCache);
    }
}
