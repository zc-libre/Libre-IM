package com.libre.im.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.im.web.mapper.UserMapper;
import com.libre.im.web.pojo.LibreUser;
import com.libre.im.web.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author ZC
 * @date 2021/8/14 21:54
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, LibreUser> implements UserService {
}
