package com.libre.im.web.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.libre.im.security.utils.SecurityUtil;
import com.libre.im.web.mapper.ConversationMapper;
import com.libre.im.web.pojo.ChatMessage;
import com.libre.im.web.pojo.Conversation;
import com.libre.im.web.pojo.vo.ConversationVO;
import com.libre.im.web.pojo.vo.UserVO;
import com.libre.im.web.service.ConversationService;
import com.libre.im.web.service.MessageService;
import com.libre.im.web.service.SysUserService;
import com.libre.toolkit.core.StreamUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
		List<Conversation> conversations = baseMapper
				.selectList(Wrappers.<Conversation>lambdaQuery().eq(Conversation::getUserId, userId));
		if (CollectionUtils.isEmpty(conversations)) {
			return Collections.emptyList();
		}
		Set<Long> userIds = conversations.stream().map(Conversation::getFriendId).collect(Collectors.toSet());
		if (CollectionUtils.isEmpty(userIds)) {
			return Collections.emptyList();
		}
		List<UserVO> userList = userService.findListByIds(userIds);
		if (CollectionUtils.isEmpty(userList)) {
			return Collections.emptyList();
		}

		Map<Long, UserVO> userMap = StreamUtils.map(userList, UserVO::getId, Function.identity());
		ArrayListMultimap<Long, ChatMessage> messageMap = messageService.findMessagesBySendUserIds(userIds);
		List<ConversationVO> vos = Lists.newArrayList();

		for (Long id : userIds) {
			if (!messageMap.containsKey(id)) {
				continue;
			}
			UserVO userVO = userMap.get(id);
			if (Objects.isNull(userVO)) {
				continue;
			}
			List<ChatMessage> chatMessages = messageMap.get(id);
			if (CollectionUtils.isEmpty(chatMessages)) {
				continue;
			}
			ConversationVO conversation = new ConversationVO();
			conversation.setUserId(userId);
			conversation.setUnreadMessageNum(chatMessages.size());
			conversation.setLastMessage(chatMessages.get(0));
			vos.add(conversation);
		}
		return vos;
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
		Long userId = SecurityUtil.getUserId();
		Conversation conversation = Conversation.of(userId, friend, LocalDateTime.now());
		this.save(conversation);
	}

}
