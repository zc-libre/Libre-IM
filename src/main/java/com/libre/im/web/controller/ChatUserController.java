package com.libre.im.web.controller;

import com.libre.core.result.R;
import com.libre.im.web.pojo.ChatFriend;
import com.libre.im.web.pojo.ChatUser;
import com.libre.im.web.service.ChatFriendService;
import com.libre.im.web.service.ChatUserService;
import com.libre.im.web.vo.ChatFriendVO;
import lombok.RequiredArgsConstructor;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author: Libre
 * @Date: 2022/5/3 2:45 AM
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class ChatUserController {
    private final ChatUserService chatUserService;
    private final ChatFriendService chatFriendService;

    // 简单测试使用
    @PostMapping("/login")
    public R<ChatUser> login(@RequestBody ChatUser user) {
        ChatUser chatUser = Optional.ofNullable(chatUserService.findByUsername(user.getUsername())).orElseThrow(() -> new IllegalArgumentException("用户不存在"));
        String pwd = chatUser.getPassword();
        if (ObjectUtils.nullSafeEquals(user.getPassword(), pwd)) {
            return R.data(chatUser);
        }
        return R.fail("密码错误");
    }

    @GetMapping("/info/{userId}")
    public R<ChatUser> info(@PathVariable Long userId) {
        ChatUser chatUser = chatUserService.getById(userId);
        return R.data(chatUser);
    }

    @GetMapping("/friend/list/{userId}")
    public R<List<ChatFriendVO>> list(@PathVariable Long userId) {
        List<ChatFriendVO> chatFriends = chatFriendService.findListByUserId(userId);
        return R.data(chatFriends);
    }

}
