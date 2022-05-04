package com.libre.im.web.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: Libre
 * @Date: 2022/5/3 2:47 AM
 */
@Data
@TableName("im_friend")
public class ChatFriend {

    @TableId
    private Long id;

    private Long chatUserId;

    private Long friendId;

    private Boolean isTop;

    private LocalDateTime addTime;
}
