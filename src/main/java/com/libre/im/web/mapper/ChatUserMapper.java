package com.libre.im.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.libre.im.web.pojo.ChatUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ZC
 * @date 2021/8/14 21:40
 */
@Mapper
public interface ChatUserMapper extends BaseMapper<ChatUser> {
}