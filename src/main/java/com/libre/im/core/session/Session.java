package com.libre.im.core.session;

/**
 * @author ZC
 * @date 2021/8/7 22:10
 */
public interface Session {

    /**
     * 获取session Id
     * @return session id
     */
    Long getId();

    /**
     * 设置key
     * @param key /
     */
    void setKey(String key);

    /**
     * 获取key
     * @return /
     */
    String getKey();


    /**
     * 关闭
     */
    void close();

    /**
     * 添加属性
     * @param key /
     * @param value /
     */
    void addAttribute(Object key, Object value);

    /**
     * 获取属性
     * @param key /
     * @param <T> /
     * @return /
     */
    <T> T getAttribute(Object key);

    /**
     * 获取客户端地址
     * @return /
     */
    String getRemoteAddress();
}
