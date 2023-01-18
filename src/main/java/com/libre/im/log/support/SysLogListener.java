
package com.libre.im.log.support;

import com.libre.boot.exception.LibreErrorEvent;
import com.libre.im.log.service.SysLogService;
import com.libre.im.log.pojo.SysLog;
import com.libre.ip2region.core.Ip2regionSearcher;
import com.libre.toolkit.core.StringUtil;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

/**
 * 监听系统日志事件，系统日志入库
 *
 * @author L.cm
 */
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class SysLogListener {

	private final SysLogService sysLogService;

	private final Ip2regionSearcher searcher;

	@Async
	@Order
	@EventListener(SysLogEvent.class)
	public void saveSysLog(SysLogEvent event) {
		SysLogMapping mapping = SysLogMapping.INSTANCE;
		SysLog sysLog = mapping.sourceToTarget(event);
		// 获取操作系统和浏览器信息
		UserAgent userAgent = UserAgent.parseUserAgentString(event.getUserAgent());
		OperatingSystem system = userAgent.getOperatingSystem();
		sysLog.setOs(system.getName());
		Browser browser = userAgent.getBrowser();
		sysLog.setBrowser(browser.getName());
		String requestIp = event.getRequestIp();
		// ip 不为空，查找 ip 的地理信息
		if (StringUtil.isNotBlank(requestIp)) {
			String address = searcher.getAddress(requestIp);
			if (StringUtil.isBlank(address) && StringUtil.equals(requestIp, "127.0.0.1")) {
				address = "内网IP";
			}
			sysLog.setAddress(address);
		}
		sysLogService.save(sysLog);
	}

	@Async
	@Order
	@EventListener(LibreErrorEvent.class)
	public void saveErrorEvent(LibreErrorEvent event) {
		// SysLogMapping mapping = SysLogMapping.INSTANCE;
		// SysLog sysLog = mapping.convertToSysLog(event);
		// AuthUser authUser = SecurityUtil.getUser();
		// Optional.ofNullable(SecurityUtil.getUser()).ifPresent(user -> {
		// sysLog.setUserId(user.getUserId());
		// sysLog.setUsername(user.getUsername());
		// });
		// sysLog.setSuccess(SysLogConstant.FAILED);
		// sysLogService.save(sysLog);
	}

}
