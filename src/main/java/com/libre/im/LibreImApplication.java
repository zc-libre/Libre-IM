package com.libre.im;

import com.libre.im.core.config.WebsocketServerProperties;
import com.libre.im.core.server.LibreWebsocketServer;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ZC
 * @date 2021/7/31 22:52
 */
@SpringBootApplication
@RequiredArgsConstructor
public class LibreImApplication implements ApplicationRunner {

     private final LibreWebsocketServer websocketServer;

    public static void main(String[] args) {
        SpringApplication.run(LibreImApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        websocketServer.run();
    }
}
