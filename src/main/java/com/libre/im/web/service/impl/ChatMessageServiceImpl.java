package com.libre.im.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.im.web.mapper.ChatMessageMapper;
import com.libre.im.web.pojo.ChatMessage;
import com.libre.im.web.service.ChatMessageService;
import org.springframework.stereotype.Service;

/**
 * @author: Libre
 * @Date: 2022/5/23 10:54 PM
 */
@Service
public class ChatMessageServiceImpl extends ServiceImpl<ChatMessageMapper, ChatMessage> implements ChatMessageService {

}
