package com.libre.im.websocket.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author: Libre
 * @Date: 2022/6/13 10:56 PM
 */
@Data
@TableName("im_conversation")
@NoArgsConstructor
public class Conversation implements Serializable {

    @TableId
    private Long id;

    private Long userId;

    private Long friendId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    private Conversation(Long userId, Long friendId, LocalDateTime createTime) {
        this.userId = userId;
        this.friendId = friendId;
        this.createTime = createTime;
    }

    public static Conversation of(Long userId, Long friendId, LocalDateTime createTime) {
        return new Conversation(userId, friendId, createTime);
    }
}
