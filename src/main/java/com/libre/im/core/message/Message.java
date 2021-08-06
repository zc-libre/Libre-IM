package com.libre.im.core.message;

import com.libre.im.core.pojo.ChatUser;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ZC
 * @date 2021/8/1 15:20
 */
@Data
public abstract class Message implements Serializable {

    private Integer type;

    private ChatUser from;

    private ChatUser accept;

    private Integer status;

    private String message;

    private LocalDateTime createTime;

}
