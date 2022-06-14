package com.libre.im.web.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.im.web.mapper.ConversationMapper;
import com.libre.im.web.pojo.Conversation;
import com.libre.im.web.service.ConversationService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Libre
 * @Date: 2022/6/13 11:00 PM
 */
@Service
public class ConversationServiceImpl extends ServiceImpl<ConversationMapper, Conversation>
		implements ConversationService {

	@Override
	public List<Conversation> listByUserId(Long userId) {
		return baseMapper.selectList(Wrappers.<Conversation>lambdaQuery().eq(Conversation::getUserId, userId));
	}

}
