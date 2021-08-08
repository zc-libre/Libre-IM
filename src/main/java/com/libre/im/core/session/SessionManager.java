package com.libre.im.core.session;

/**
 * @author ZC
 * @date 2021/8/8 16:27
 */
public interface SessionManager {

    /**
     * 获取session
     * @param key /
     * @return /
     */
    Session get(String key);
    /**
     * 保存session
     * @param session /
     */
    void put(Session session);

    /**
     * 移除session
     * @param key /
     */
    void remove(String key);

    /**
     * 删除
     * @param session /
     */
    void remove(Session session);

    /**
     * 是否存在session
     * @param key /
     * @return /
     */
    boolean isExist(String key);
}
