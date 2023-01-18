package com.libre.im.websocket.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.libre.im.websocket.pojo.Conversation;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: Libre
 * @Date: 2022/6/13 11:01 PM
 */
@Mapper
public interface ConversationMapper extends BaseMapper<Conversation> {
}
