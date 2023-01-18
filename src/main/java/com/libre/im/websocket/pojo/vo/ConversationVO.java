package com.libre.im.websocket.pojo.vo;

import com.libre.im.websocket.pojo.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
