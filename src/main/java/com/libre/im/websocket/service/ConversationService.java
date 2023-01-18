package com.libre.im.websocket.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.im.websocket.pojo.Conversation;
import com.libre.im.websocket.pojo.vo.ConversationVO;

import java.util.List;

/**
 * @author: Libre
 * @Date: 2022/6/13 10:55 PM
 */
public interface ConversationService extends IService<Conversation> {

    List<ConversationVO> listByUserId(Long userId);

    void removeByUserIdAndFriendId(Long userId, Long friendId);

    void add(Long friend);

}
