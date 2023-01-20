package com.libre.im.websocket.core.handler;

import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 * @author: Libre
 * @Date: 2023/1/21 4:29 AM
 */
public class WebSocketServerHandler extends WebSocketServerProtocolHandler {

    public WebSocketServerHandler(String websocketPath) {
        super(websocketPath, null, true, 10 * 1023, false, true);
    }
}
