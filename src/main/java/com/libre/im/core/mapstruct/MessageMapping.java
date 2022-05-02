package com.libre.im.core.mapstruct;

import com.libre.core.mapstruct.BaseConvert;
import com.libre.im.core.message.TextMessage;
import com.libre.im.core.proto.TextMessageProto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author: Libre
 * @Date: 2022/5/1 10:08 PM
 */
@Mapper
public interface MessageMapping extends BaseConvert<TextMessageProto.TextMessage, TextMessage> {

    MessageMapping INSTANCE = Mappers.getMapper(MessageMapping.class);

}
