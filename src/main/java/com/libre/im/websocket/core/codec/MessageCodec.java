package com.libre.im.websocket.core.codec;

/**
 * @author: Libre
 * @Date: 2022/5/1 7:36 AM
 */
public interface MessageCodec {

    void encode();

    void decode();
}
