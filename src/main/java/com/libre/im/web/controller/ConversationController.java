package com.libre.im.web.controller;

import com.libre.core.result.R;
import com.libre.im.security.utils.SecurityUtil;
import com.libre.im.web.pojo.vo.ConversationVO;
import com.libre.im.web.service.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

	@GetMapping("/list")
	public R<List<ConversationVO>> listByUserId() {
		Long userId = SecurityUtil.getUserId();
		List<ConversationVO> conversations = conversationService.listByUserId(userId);
		return R.data(conversations);
	}


	@PutMapping
	public R<Boolean> add(@RequestParam Long friend) {
		conversationService.add(friend);
		return R.status(Boolean.TRUE);
	}

	@DeleteMapping
	public R<Boolean> remove(@RequestParam Long friendId) {
		Long userId = SecurityUtil.getUserId();
		conversationService.removeByUserIdAndFriendId(userId, friendId);
		return R.status(Boolean.TRUE);
	}
}
