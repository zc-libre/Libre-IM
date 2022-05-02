package com.libre.im.core.session;

/**
 * @author ZC
 * @date 2021/8/8 16:27
 */
public interface SessionManager {

    /**
     * 获取session
     * @param userId /
     * @return /
     */
    Session get(Long userId);

    /**
     * 保存session
     * @param userId /
     * @param Session /
     */
    void put(Long userId, Session Session);


    /**
     * 移除session
     * @param userId /
     */
    void remove(Long userId);

    /**
     * 删除
     * @param Session /
     */
    void remove(Session Session);

    /**
     * 是否存在session
     * @param userId /
     * @return /
     */
    boolean isExist(Long userId);
}
