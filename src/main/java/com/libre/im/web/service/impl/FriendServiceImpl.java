package com.libre.im.web.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.libre.core.toolkit.CollectionUtil;
import com.libre.core.toolkit.StreamUtils;
import com.libre.im.web.constant.CacheKey;
import com.libre.im.web.mapper.FriendMapper;
import com.libre.im.web.pojo.Friend;
import com.libre.im.web.pojo.LibreUser;
import com.libre.im.web.service.FriendService;
import com.libre.im.web.service.SysUserService;
import com.libre.im.web.service.mapstruct.ChatFriendMapping;
import com.libre.im.web.pojo.vo.ChatFriendVO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author: Libre
 * @Date: 2022/5/3 2:49 AM
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = CacheKey.FRIEND_CACHE_KEY)
public class FriendServiceImpl extends ServiceImpl<FriendMapper, Friend> implements FriendService {

	private final SysUserService sysUserService;

	@Override
	//@Cacheable(key = "#userId")
	public List<ChatFriendVO> findListByUserId(Long userId) {
		List<Friend> friends = this.list(Wrappers.<Friend>lambdaQuery().eq(Friend::getUserId, userId));
		if (CollectionUtil.isEmpty(friends)) {
			return Collections.emptyList();
		}
		ChatFriendMapping mapping = ChatFriendMapping.INSTANCE;
		Map<Long, Friend> friendMap = StreamUtils.map(friends, Friend::getFriendId, chatFriend -> chatFriend);
		List<LibreUser> libreUsers = sysUserService.listByIds(friendMap.keySet());
		List<ChatFriendVO> voList = Lists.newArrayList();
		for (LibreUser libreUser : libreUsers) {
			Friend chatFriend = friendMap.get(libreUser.getId());
			Optional.ofNullable(chatFriend).ifPresent(friend -> {
				ChatFriendVO chatFriendVO = mapping.chatUserToFriendVO(libreUser);
				chatFriendVO.setIsTop(chatFriend.getIsTop());
				chatFriendVO.setAddTime(chatFriend.getAddTime());
				voList.add(chatFriendVO);
			});
		}
		return voList;
	}

	@Override
	@CacheEvict(allEntries = true)
	public void removeFriend(Long friend) {

	}

}
