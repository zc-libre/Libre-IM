package com.libre.im.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author ZC
 * @date 2021/8/1 13:28
 */
@Data
@ConfigurationProperties("libre.im")
public class WebsocketServerProperties {

    private Integer port = 8081;

    private String wsUri = "/ws";

    private String wsFactoryUri = "/websocket";
}
