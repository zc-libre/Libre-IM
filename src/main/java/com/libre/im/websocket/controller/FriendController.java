package com.libre.im.websocket.controller;

import com.libre.im.websocket.pojo.vo.FriendVO;
import com.libre.toolkit.result.R;
import com.libre.im.websocket.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: Libre
 * @Date: 2022/5/3 2:55 AM
 */
@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @GetMapping("/list/{userId}")
    public R<List<FriendVO>> list(@PathVariable Long userId) {
        List<FriendVO> chatFriends = friendService.findListByUserId(userId);
        return R.data(chatFriends);
    }

}
