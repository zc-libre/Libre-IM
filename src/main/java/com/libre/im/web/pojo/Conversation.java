package com.libre.im.web.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author: Libre
 * @Date: 2022/6/13 10:56 PM
 */
@Data
@TableName("im_conversation")
public class Conversation implements Serializable {

    @TableId
    private Long id;

    private Long userId;

    private Long friendId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
