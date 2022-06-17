package com.libre.im.web.service.mapstruct;

import com.libre.core.mapstruct.BaseConvert;
import com.libre.im.web.pojo.Friend;
import com.libre.im.web.pojo.LibreUser;
import com.libre.im.web.pojo.vo.FriendVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author: Libre
 * @Date: 2022/5/3 7:55 AM
 */
@Mapper
public interface FriendMapping extends BaseConvert<Friend, FriendVO> {

    FriendMapping INSTANCE = Mappers.getMapper(FriendMapping.class);

    @Mapping(source = "id", target = "friendId")
    FriendVO chatUserToFriendVO(LibreUser user);
}
