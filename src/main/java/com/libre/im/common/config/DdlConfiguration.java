package com.libre.im.common.config;

import com.google.common.collect.ImmutableList;
import com.libre.mybatis.ddl.DdlApplicationRunner;
import com.libre.mybatis.ddl.IDdl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author: Libre
 * @Date: 2022/12/13 12:45 AM
 */
@Configuration(proxyBeanMethods = false)
public class DdlConfiguration {

	@Bean
	public IDdl ddl(DataSource dataSource) {
		return new PostgresDdl(dataSource);
	}

	@Bean
	public DdlApplicationRunner ddlApplicationRunner(IDdl ddl) {
		return new DdlApplicationRunner(ImmutableList.of(ddl));
	}

}
