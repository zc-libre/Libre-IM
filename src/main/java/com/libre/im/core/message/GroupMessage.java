package com.libre.im.core.message;

import com.libre.im.core.pojo.ChatUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author ZC
 * @date 2021/8/6 23:27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class GroupMessage extends Message {

    private Set<Long> chatGroup;
}
