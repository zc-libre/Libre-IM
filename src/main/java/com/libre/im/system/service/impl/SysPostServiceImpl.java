package com.libre.im.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.im.system.mapper.SysPostMapper;
import com.libre.im.system.pojo.entity.SysPost;
import com.libre.im.system.pojo.entity.SysUserPost;
import com.libre.im.system.service.SysPostService;
import com.libre.im.system.service.SysUserPostService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zhao.cheng
 */
@Service
@RequiredArgsConstructor
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost> implements SysPostService {

	private final SysUserPostService userPostService;

	@Override
	public List<SysPost> getListByUserId(Long userId) {
		List<SysUserPost> userPostList = userPostService.getListByUserId(userId);
		if (CollectionUtils.isEmpty(userPostList)) {
			return Collections.emptyList();
		}
		Set<Long> postIds = userPostList.stream().map(SysUserPost::getPostId).collect(Collectors.toSet());

		return super.listByIds(postIds);
	}

}
