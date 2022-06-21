package com.libre.im.web.controller;

import com.libre.core.result.R;
import com.libre.im.web.pojo.Conversation;
import com.libre.im.web.pojo.vo.ConversationVO;
import com.libre.im.web.service.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: Libre
 * @Date: 2022/6/13 11:09 PM
 */
@RestController
@RequestMapping("/conversation")
@RequiredArgsConstructor
public class ConversationController {

    private final ConversationService conversationService;

    @GetMapping("/list/{userId}")
    public R<List<ConversationVO>> listByUserId(@PathVariable Long userId) {
        List<ConversationVO> conversations = conversationService.listByUserId(userId);
        return R.data(conversations);
    }
}
