package com.libre.im.core.message;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author ZC
 * @date 2021/8/6 23:27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TextMessage extends Message {

    @Override
    public String getBody() {
        return (String) body;
    }

}
