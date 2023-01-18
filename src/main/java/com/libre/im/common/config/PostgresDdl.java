package com.libre.im.common.config;

import com.google.common.collect.Lists;
import com.libre.mybatis.ddl.SimpleDdl;

import javax.sql.DataSource;
import java.util.List;

public class PostgresDdl extends SimpleDdl {

	private final DataSource dataSource;

	public PostgresDdl(DataSource dataSource) {
		super(dataSource);
		this.dataSource = dataSource;
	}

	/**
	 * 执行 SQL 脚本方式
	 */
	@Override
	public List<String> getSqlFiles() {
		return Lists.newArrayList("db/postgres/libre_table.sql", "db/postgres/libre_data.sql");
	}

}
