package com.libre.im.web.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.im.web.mapper.LibreUserMapper;
import com.libre.im.web.pojo.LibreUser;
import com.libre.im.web.service.LibreUserService;
import org.springframework.stereotype.Service;

/**
 * @author ZC
 * @date 2021/8/14 21:54
 */
@Service
public class LibreUserServiceImpl extends ServiceImpl<LibreUserMapper, LibreUser> implements LibreUserService {
    @Override
    public LibreUser findByUsername(String username) {
        return this.getOne(Wrappers.<LibreUser>lambdaQuery().eq(LibreUser::getUsername, username));
    }
}
