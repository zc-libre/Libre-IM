package com.libre.im.websocket.message;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author ZC
 * @date 2021/8/6 23:27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MediaMessage extends Message {

    @Override
    public Byte[] getBody() {
        return (Byte[]) body;
    }

}
