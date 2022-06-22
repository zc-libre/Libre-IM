package com.libre.im.web.pojo.vo;

import com.libre.im.web.pojo.ChatMessage;
import com.libre.im.web.pojo.Friend;
import com.libre.im.web.pojo.LibreUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author: Libre
 * @Date: 2022/6/18 8:39 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversationVO implements Serializable {

    private Long userId;

    private UserVO user;

    private ChatMessage lastMessage;

    private Integer unreadMessageNum;

}
