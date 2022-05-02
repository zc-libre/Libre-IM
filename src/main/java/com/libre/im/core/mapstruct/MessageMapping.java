package com.libre.im.core.mapstruct;

import com.libre.core.mapstruct.BaseConvert;
import com.libre.core.time.DatePattern;
import com.libre.im.core.message.TextMessage;
import com.libre.im.core.proto.TextMessageProto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author: Libre
 * @Date: 2022/5/1 10:08 PM
 */
@Mapper
public interface MessageMapping extends BaseConvert<TextMessageProto.TextMessage, TextMessage> {

    MessageMapping INSTANCE = Mappers.getMapper(MessageMapping.class);

    @Override
    @Mapping(source = "createTime", target = "createTime", dateFormat = DatePattern.NORM_DATETIME_PATTERN)
    TextMessage sourceToTarget(TextMessageProto.TextMessage textMessage);
}
