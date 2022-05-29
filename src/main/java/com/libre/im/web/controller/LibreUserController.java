package com.libre.im.web.controller;

import com.libre.core.result.R;
import com.libre.im.web.pojo.LibreUser;
import com.libre.im.web.service.FriendService;
import com.libre.im.web.service.SysUserService;
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
public class LibreUserController {
    private final SysUserService sysUserService;
    private final FriendService friendService;

    // 简单测试使用
    @PostMapping("/login")
    public R<LibreUser> login(@RequestBody LibreUser user) {
        LibreUser libreUser = Optional.ofNullable(sysUserService.findByUsername(user.getUsername())).orElseThrow(() -> new IllegalArgumentException("用户不存在"));
        String pwd = libreUser.getPassword();
        if (ObjectUtils.nullSafeEquals(user.getPassword(), pwd)) {
            return R.data(libreUser);
        }
        return R.fail("密码错误");
    }

    @GetMapping("/info/{userId}")
    public R<LibreUser> info(@PathVariable Long userId) {
        LibreUser libreUser = sysUserService.getById(userId);
        return R.data(libreUser);
    }

    @GetMapping("/friend/list/{userId}")
    public R<List<ChatFriendVO>> list(@PathVariable Long userId) {
        List<ChatFriendVO> chatFriends = friendService.findListByUserId(userId);
        return R.data(chatFriends);
    }

}
