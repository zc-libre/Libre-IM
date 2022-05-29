package com.libre.im.websocket.exception;

/**
 * @author: Libre
 * @Date: 2022/5/2 9:42 PM
 */
public class LibreImException extends RuntimeException {

    public LibreImException(String message) {
        super(message);
    }

    public LibreImException(String message, Throwable cause) {
        super(message, cause);
    }


}
