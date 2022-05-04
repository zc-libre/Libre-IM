package com.libre.im.web.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.libre.core.toolkit.CollectionUtil;
import com.libre.core.toolkit.StreamUtils;
import com.libre.im.web.mapper.ChatFriendMapper;
import com.libre.im.web.pojo.ChatFriend;
import com.libre.im.web.pojo.ChatUser;
import com.libre.im.web.service.ChatFriendService;
import com.libre.im.web.service.ChatUserService;
import com.libre.im.web.service.mapstruct.ChatFriendMapping;
import com.libre.im.web.vo.ChatFriendVO;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author: Libre
 * @Date: 2022/5/3 2:49 AM
 */
@Service
@RequiredArgsConstructor
public class ChatUserFriendServiceImpl extends ServiceImpl<ChatFriendMapper, ChatFriend> implements ChatFriendService {

    private final ChatUserService chatUserService;

    @Override
    public List<ChatFriendVO> findListByUserId(Long userId) {
        List<ChatFriend> chatFriends = this.list(Wrappers.<ChatFriend>lambdaQuery().eq(ChatFriend::getChatUserId, userId));
        if (CollectionUtil.isEmpty(chatFriends)) {
            return Collections.emptyList();
        }
        ChatFriendMapping mapping = ChatFriendMapping.INSTANCE;
        Map<Long, ChatFriend> friendMap = StreamUtils.map(chatFriends, ChatFriend::getFriendId, chatFriend -> chatFriend);
        List<ChatUser> chatUsers = chatUserService.listByIds(friendMap.keySet());
        List<ChatFriendVO> voList = Lists.newArrayList();
        for (ChatUser chatUser : chatUsers) {
            ChatFriend chatFriend = friendMap.get(chatUser.getId());
            Optional.ofNullable(chatFriend).ifPresent(friend -> {
                ChatFriendVO chatFriendVO = mapping.chatUserToFriendVO(chatUser);
                chatFriendVO.setIsTop(chatFriend.getIsTop());
                chatFriendVO.setAddTime(chatFriend.getAddTime());
                voList.add(chatFriendVO);
            });
        }
        return voList;
    }
}
