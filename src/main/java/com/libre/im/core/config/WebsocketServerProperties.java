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

    /**
     * 端口
     */
    private Integer port = 8081;

    /**
     * url
     */
    private String wsUri = "/ws";

    /**
     * 读超时  s
     */
    private Integer readIdleTimeOut = 60;

    /**
     * 写超时
     */
    private Integer writeIdleTimeOut = 10;

    /**
     * 所有超时
     */
    private Integer allIdleTimeOut = 10;
}
