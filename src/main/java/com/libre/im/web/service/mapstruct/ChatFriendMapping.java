package com.libre.im.web.service.mapstruct;

import com.libre.core.mapstruct.BaseConvert;
import com.libre.im.web.pojo.ChatFriend;
import com.libre.im.web.pojo.ChatUser;
import com.libre.im.web.vo.ChatFriendVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author: Libre
 * @Date: 2022/5/3 7:55 AM
 */
@Mapper
public interface ChatFriendMapping extends BaseConvert<ChatFriend, ChatFriendVO> {

    ChatFriendMapping INSTANCE = Mappers.getMapper(ChatFriendMapping.class);

    @Mapping(source = "id", target = "friendId")
    ChatFriendVO chatUserToFriendVO(ChatUser user);
}
