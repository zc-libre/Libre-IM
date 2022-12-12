package com.libre.im.web.service.mapstruct;

import com.libre.toolkit.mapstruct.BaseMapping;
import com.libre.im.web.pojo.LibreUser;
import com.libre.im.web.pojo.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author: Libre
 * @Date: 2022/6/21 9:52 PM
 */
@Mapper
public interface UserMapping extends BaseMapping<LibreUser, UserVO> {

	UserMapping INSTANCE = Mappers.getMapper(UserMapping.class);

}
