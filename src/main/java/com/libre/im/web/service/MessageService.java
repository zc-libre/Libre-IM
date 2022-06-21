package com.libre.im.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.libre.im.web.pojo.ChatMessage;

import java.util.Collection;

/**
 * @author: Libre
 * @Date: 2022/5/23 10:54 PM
 */
public interface MessageService extends IService<ChatMessage> {

    ArrayListMultimap<Long, ChatMessage> findMessagesBySendUserIds(Collection<Long> userIds);
}
