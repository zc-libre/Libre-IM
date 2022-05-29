package com.libre.im.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.im.security.pojo.SysRole;

import java.util.List;

/**
 * @author: Libre
 * @Date: 2022/5/29 11:45 PM
 */
public interface SysRoleService extends IService<SysRole> {
    List<SysRole> getListByUserId(Long userId);
}
