package com.libre.im.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.im.web.mapper.GroupUserMapper;
import com.libre.im.web.pojo.GroupUser;
import com.libre.im.web.service.GroupUserService;
import org.springframework.stereotype.Service;

/**
 * @author ZC
 * @date 2021/8/14 21:55
 */
@Service
public class GroupUserServiceImpl extends ServiceImpl<GroupUserMapper, GroupUser> implements GroupUserService {
}
