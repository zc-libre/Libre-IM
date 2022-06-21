package com.libre.im.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.im.web.pojo.LibreUser;
import com.libre.im.web.pojo.vo.UserVO;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author ZC
 * @date 2021/8/14 21:52
 */
public interface SysUserService extends IService<LibreUser> {

	LibreUser findByUsername(String username);

    boolean updateByUsername(String username, LibreUser sysUser);

    List<UserVO> findListByIds(Collection<Long> userIds);
}
