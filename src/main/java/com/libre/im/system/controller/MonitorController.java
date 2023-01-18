package com.libre.im.system.controller;

import com.libre.im.system.service.MonitorService;
import com.libre.toolkit.result.R;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * @author: Libre
 * @Date: 2022/12/18 7:37 PM
 */

@RequestMapping("/api/sys/monitor")
@RestController
@RequiredArgsConstructor
public class MonitorController {

	private final MonitorService monitorService;

	private final DataSource dataSource;

	@GetMapping("/server")
	@Operation(summary = "服务器监控")
	@PreAuthorize("@sec.hasPermission('system:monitor:servers')")
	public R<Map<String, Object>> server() {
		Map<String, Object> serverInfo = monitorService.getServerInfo();
		return R.data(serverInfo);
	}

//	@GetMapping("sql")
//	@Operation(summary = "sql监控")
//	@PreAuthorize("@sec.hasPermission('system:monitor:sql')")
//	public R<List<Map<String, Object>>> sqlStat() {
//		DruidStatManagerFacade statManagerFacade = DruidStatManagerFacade.getInstance();
//		return R.data(statManagerFacade.getSqlStatDataList(dataSource));
//	}

	@GetMapping("redis")
	@Operation(summary = "redis监控")
	@PreAuthorize("@sec.hasPermission('system:monitor:redis')")
	public R<Map<String, Object>> redisStat() {
		Map<String, Object> redisStat = monitorService.getRedisStat();
		return R.data(redisStat);
	}

}
