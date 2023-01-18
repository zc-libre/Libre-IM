package com.libre.im.websocket.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.im.system.service.SysUserService;
import com.libre.im.websocket.service.FriendService;
import com.libre.im.websocket.constant.CacheKey;
import com.libre.im.websocket.mapper.FriendMapper;
import com.libre.im.websocket.pojo.Friend;
import com.libre.im.websocket.pojo.vo.FriendVO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
	@Cacheable(key = "#userId")
	public List<FriendVO> findListByUserId(Long userId) {
//		List<Friend> friends = this.list(Wrappers.<Friend>lambdaQuery().eq(Friend::getUserId, userId));
//		if (CollectionUtils.isEmpty(friends)) {
//			return Collections.emptyList();
//		}
//
//		Map<Long, Friend> friendMap = StreamUtils.map(friends, Friend::getFriendId, chatFriend -> chatFriend);
//		List<LibreUser> libreUsers = sysUserService.listByIds(friendMap.keySet());
//
//		List<FriendVO> voList = Lists.newArrayList();
//		FriendMapping mapping = FriendMapping.INSTANCE;
//		for (LibreUser libreUser : libreUsers) {
//			Friend friend = friendMap.get(libreUser.getId());
//			if (Objects.isNull(friend)) {
//				continue;
//			}
//			FriendVO friendVO = mapping.chatUserToFriendVO(libreUser);
//			friendVO.setIsTop(friend.getIsTop());
//			friendVO.setAddTime(friend.getAddTime());
//			voList.add(friendVO);
//		}
//		return voList;
		return null;
	}

	@Override
	@CacheEvict(allEntries = true)
	public void removeFriend(Long friendId) {

	}

}
