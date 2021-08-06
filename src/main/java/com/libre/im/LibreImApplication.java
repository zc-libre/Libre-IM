package com.libre.im;

import com.libre.im.core.config.WebsocketServerProperties;
import com.libre.im.core.server.LibreWebsocketServer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ZC
 * @date 2021/7/31 22:52
 */
@SpringBootApplication
@RequiredArgsConstructor
public class LibreImApplication implements CommandLineRunner {

    private final WebsocketServerProperties properties;

    public static void main(String[] args) {
        SpringApplication.run(LibreImApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        LibreWebsocketServer.run(properties.getPort());
    }
}
