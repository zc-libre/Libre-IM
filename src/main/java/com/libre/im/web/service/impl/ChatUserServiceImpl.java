package com.libre.im.web.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.im.web.mapper.ChatUserMapper;
import com.libre.im.web.pojo.ChatUser;
import com.libre.im.web.service.ChatUserService;
import org.springframework.stereotype.Service;

/**
 * @author ZC
 * @date 2021/8/14 21:54
 */
@Service
public class ChatUserServiceImpl extends ServiceImpl<ChatUserMapper, ChatUser> implements ChatUserService {
    @Override
    public ChatUser findByUsername(String username) {
        return this.getOne(Wrappers.<ChatUser>lambdaQuery().eq(ChatUser::getUsername, username));
    }
}
