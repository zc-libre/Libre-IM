package com.libre.im.admin.security.constant;

/**
 * @author: Libre
 * @Date: 2022/5/29 11:57 PM
 */
public interface SecurityConstants {

    String PASSWORD_PREFIX = "{bcrypt}";

    Integer LOCKED_NO = 0;
    Integer LOCKED_YES = 1;

    Integer IS_ADMIN = 0;
    Integer NOT_ADMIN = 1;
}
