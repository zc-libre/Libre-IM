package com.libre.im.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.im.web.pojo.Friend;
import com.libre.im.web.pojo.vo.FriendVO;

import java.util.Collection;
import java.util.List;

/**
 * @author: Libre
 * @Date: 2022/5/3 2:48 AM
 */
public interface FriendService extends IService<Friend> {

    List<FriendVO> findListByUserId(Long userId);

    void removeFriend(Long friendId);


}
