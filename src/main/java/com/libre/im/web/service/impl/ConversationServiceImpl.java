package com.libre.im.web.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.libre.core.toolkit.StreamUtils;
import com.libre.im.web.mapper.ConversationMapper;
import com.libre.im.web.pojo.ChatMessage;
import com.libre.im.web.pojo.Conversation;
import com.libre.im.web.pojo.LibreUser;
import com.libre.im.web.pojo.vo.ConversationVO;
import com.libre.im.web.pojo.vo.UserVO;
import com.libre.im.web.service.ConversationService;
import com.libre.im.web.service.FriendService;
import com.libre.im.web.service.MessageService;
import com.libre.im.web.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
			List<ChatMessage> chatMessages = messageMap.get(id);
			UserVO userVO = userMap.get(id);
			vos.add(ConversationVO.of(userId, userVO, chatMessages));
		}
		return vos;
	}

}
