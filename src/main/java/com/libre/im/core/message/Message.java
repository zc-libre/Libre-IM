package com.libre.im.core.message;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ZC
 * @date 2021/8/1 15:20
 */
@Data
@NoArgsConstructor
public abstract class Message implements Serializable {

    private Long id;

    private Integer type;

    private Long sendUserId;

    private Integer status;

    private String message;

    private LocalDateTime createTime;

}
