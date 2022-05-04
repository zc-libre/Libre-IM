package com.libre.im.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.im.web.pojo.ChatUser;

/**
 * @author ZC
 * @date 2021/8/14 21:52
 */
public interface ChatUserService extends IService<ChatUser>{
    ChatUser findByUsername(String username);
}
