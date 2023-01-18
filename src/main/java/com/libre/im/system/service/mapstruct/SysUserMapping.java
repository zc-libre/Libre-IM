package com.libre.im.system.service.mapstruct;

import com.libre.im.common.security.dto.AuthUser;
import com.libre.im.security.pojo.dto.UserInfo;
import com.libre.im.system.pojo.dto.UserDTO;
import com.libre.im.system.pojo.entity.SysUser;
import com.libre.im.system.pojo.vo.UserVO;
import com.libre.toolkit.mapstruct.BaseMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Libre
 * @date 2022/1/1 18:34
 */
@Mapper
public interface SysUserMapping extends BaseMapping<SysUser, UserVO> {

	SysUserMapping INSTANCE = Mappers.getMapper(SysUserMapping.class);

	SysUser convertToUser(UserDTO userDTO);

	UserInfo convertToUserInfo(AuthUser authUser);

}
