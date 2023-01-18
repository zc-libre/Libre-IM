package com.libre.im.websocket.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.ArrayListMultimap;
import com.libre.im.websocket.constant.MessageConstant;
import com.libre.im.websocket.pojo.ChatMessage;
import com.libre.toolkit.core.StreamUtils;
import com.libre.im.websocket.mapper.MessageMapper;
import com.libre.im.websocket.service.MessageService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: Libre
 * @Date: 2022/5/23 10:54 PM
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, ChatMessage> implements MessageService {

	@Override
	public ArrayListMultimap<Long, ChatMessage> findMessagesBySendUserIds(Collection<Long> userIds) {
		ArrayListMultimap<Long, ChatMessage> messageMultimap = ArrayListMultimap.create();
		if (CollectionUtils.isEmpty(userIds)) {
			return messageMultimap;
		}
		List<ChatMessage> messageList = this.list(Wrappers.<ChatMessage>lambdaQuery()
				.eq(ChatMessage::getStatus, MessageConstant.UNREAD).in(ChatMessage::getSendUserId, userIds));
		if (CollectionUtils.isEmpty(messageList)) {
			return messageMultimap;
		}
		Map<Long, List<ChatMessage>> messageMap = StreamUtils.listGroupBy(messageList, ChatMessage::getSendUserId);
		messageMap.forEach((userId, messages) -> {
			List<ChatMessage> chatMessages = messages.stream()
					.sorted(Comparator.comparing(ChatMessage::getCreateTime).reversed()).collect(Collectors.toList());
			messageMultimap.putAll(userId, chatMessages);
		});
		return messageMultimap;
	}

}
