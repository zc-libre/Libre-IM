package com.libre.im.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.im.web.pojo.Conversation;

import java.util.List;

/**
 * @author: Libre
 * @Date: 2022/6/13 10:55 PM
 */
public interface ConversationService extends IService<Conversation> {

    List<Conversation> listByUserId(Long userId);
}
