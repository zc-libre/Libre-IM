package com.libre.im.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.im.web.mapper.MessageMapper;
import com.libre.im.web.pojo.ChatMessage;
import com.libre.im.web.service.MessageService;
import org.springframework.stereotype.Service;

/**
 * @author: Libre
 * @Date: 2022/5/23 10:54 PM
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, ChatMessage> implements MessageService {

}
