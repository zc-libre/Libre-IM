package com.libre.im.web.constant;

/**
 * @author: Libre
 * @Date: 2022/5/30 12:15 AM
 */
public interface CacheKey {

    String CACHE_KEY_PREFIX = "libre:im:";
    String USER_CACHE_KEY =  CACHE_KEY_PREFIX + "user";
    String FRIEND_CACHE_KEY = CACHE_KEY_PREFIX + "friend";

}
