package com.libre.im.security.exception;

/**
 * @author: Libre
 * @Date: 2022/6/3 1:16 AM
 */
public class UserLockedException extends RuntimeException {

    public UserLockedException(String message) {
        super(message);
    }

    public UserLockedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserLockedException(Throwable cause) {
        super(cause);
    }
}
