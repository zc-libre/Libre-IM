package com.libre.im.common.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.libre.im.common.security.dto.AuthUser;
import com.libre.im.common.security.support.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Libre
 * @date 2021/7/13 16:33
 */
@Slf4j
@Component
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

	private static final String SYSTEM = "System";

	@Override
	public void insertFill(MetaObject metaObject) {
		this.strictInsertFill(metaObject, "gmtCreate", LocalDateTime.class, LocalDateTime.now());
		this.strictInsertFill(metaObject, "gmtModified", LocalDateTime.class, LocalDateTime.now());
		this.strictInsertFill(metaObject, "gmtCreateName", String.class, getUsername());
		this.strictInsertFill(metaObject, "gmtModifiedName", String.class, getUsername());
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		this.strictInsertFill(metaObject, "gmtModified", LocalDateTime.class, LocalDateTime.now());
		this.strictInsertFill(metaObject, "gmtModifiedName", String.class, getUsername());
	}

	private String getUsername() {
		AuthUser user = SecurityUtil.getUser();
		if (Objects.isNull(user)) {
			return SYSTEM;
		}
		String username = user.getUsername();
		return username == null ? SYSTEM : username;
	}

}
