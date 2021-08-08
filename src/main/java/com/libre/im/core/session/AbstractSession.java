package com.libre.im.core.session;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author ZC
 * @date 2021/8/7 22:26
 */
public abstract class AbstractSession implements Session {

    private String key;
    private final Map<Object, Object> attributeMap = Maps.newHashMap();

    @Override
    public void addAttribute(Object key, Object value) {
        attributeMap.put(key, value);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getAttribute(Object key) {
        return (T) attributeMap.get(key);
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }
}
