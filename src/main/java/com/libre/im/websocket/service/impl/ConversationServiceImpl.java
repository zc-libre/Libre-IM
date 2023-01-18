package com.libre.im.websocket.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.im.common.security.support.SecurityUtil;
import com.libre.im.system.service.SysUserService;
import com.libre.im.websocket.mapper.ConversationMapper;
import com.libre.im.websocket.pojo.Conversation;
import com.libre.im.websocket.pojo.vo.ConversationVO;
import com.libre.im.websocket.service.ConversationService;
import com.libre.im.websocket.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author: Libre
 * @Date: 2022/6/13 11:00 PM
 */
@Service
@RequiredArgsConstructor
public class ConversationServiceImpl extends ServiceImpl<ConversationMapper, Conversation>
		implements ConversationService {

	private final SysUserService userService;

	private final MessageService messageService;

	@Override
	public List<ConversationVO> listByUserId(Long userId) {
//		List<Conversation> conversations = baseMapper
//				.selectList(Wrappers.<Conversation>lambdaQuery().eq(Conversation::getUserId, userId));
//		if (CollectionUtils.isEmpty(conversations)) {
//			return Collections.emptyList();
//		}
//		Set<Long> userIds = conversations.stream().map(Conversation::getFriendId).collect(Collectors.toSet());
//		if (CollectionUtils.isEmpty(userIds)) {
//			return Collections.emptyList();
//		}
//		List<UserVO> userList = userService.deleteUserByIds(userIds);
//		if (CollectionUtils.isEmpty(userList)) {
//			return Collections.emptyList();
//		}
//
//		Map<Long, UserVO> userMap = StreamUtils.map(userList, UserVO::getId, Function.identity());
//		ArrayListMultimap<Long, ChatMessage> messageMap = messageService.findMessagesBySendUserIds(userIds);
//		List<ConversationVO> vos = Lists.newArrayList();
//
//		for (Long id : userIds) {
//			if (!messageMap.containsKey(id)) {
//				continue;
//			}
//			UserVO userVO = userMap.get(id);
//			if (Objects.isNull(userVO)) {
//				continue;
//			}
//			List<ChatMessage> chatMessages = messageMap.get(id);
//			if (CollectionUtils.isEmpty(chatMessages)) {
//				continue;
//			}
//			ConversationVO conversation = new ConversationVO();
//			conversation.setUserId(userId);
//			conversation.setUnreadMessageNum(chatMessages.size());
//			conversation.setLastMessage(chatMessages.get(0));
//			vos.add(conversation);
//		}
//		return vos;
		return null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void removeByUserIdAndFriendId(Long userId, Long friendId) {
		this.remove(Wrappers.<Conversation>lambdaQuery().eq(Conversation::getFriendId, friendId)
				.eq(Conversation::getUserId, userId));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void add(Long friend) {

		Long userId = SecurityUtil.getUser().getUserId();
		Conversation conversation = Conversation.of(userId, friend, LocalDateTime.now());
		this.save(conversation);
	}

}
