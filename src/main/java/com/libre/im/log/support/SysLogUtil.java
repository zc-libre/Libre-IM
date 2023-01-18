package com.libre.im.log.support;

import com.libre.boot.toolkit.RequestUtils;
import com.libre.im.common.security.dto.AuthUser;
import com.libre.im.common.security.support.SecurityUtil;
import com.libre.toolkit.core.*;
import org.springframework.http.HttpHeaders;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * 系统日志工具类
 */
public class SysLogUtil {

	/**
	 * 构造 SysLogEvent
	 * @param logType SysLogType
	 * @return SysLogEvent
	 */
	public static SysLogEvent buildSysLogEvent(SysLogType logType) {
		SysLogEvent event = new SysLogEvent();
		event.setLogType(logType.name());
		HttpServletRequest request = RequestUtils.getRequest();
		String method = request.getMethod();
		event.setRequestMethod(method);
		// 请求信息 GET /api/test/xx
		String requestInfo = method + StringPool.SPACE + request.getRequestURI();
		// paraMap
		Map<String, String[]> paraMap = request.getParameterMap();
		if (CollectionUtils.isEmpty(paraMap)) {
			event.setParams(requestInfo);
		}
		else {
			StringBuilder builder = new StringBuilder(requestInfo).append(CharPool.QUESTION_MARK);
			paraMap.forEach((key, values) -> {
				builder.append(key).append(CharPool.EQUALS);
				if ("password".equalsIgnoreCase(key)) {
					builder.append("******");
				}
				else {
					builder.append(StringUtil.join(values));
				}
				builder.append(CharPool.AMPERSAND);
			});
			builder.deleteCharAt(builder.length() - 1);
			event.setParams(builder.toString());
		}
		// 获取请求 ip 和 ua
		event.setRequestIp(RequestUtils.getIp());
		event.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
		// 用户信息
		AuthUser authUser = SecurityUtil.getUser();
		if (Objects.nonNull(authUser)) {
			event.setUserId(authUser.getUserId());
			event.setUsername(authUser.getUsername());
		}
		return event;
	}

	public static void initErrorInfo(Throwable error, SysLogEvent event) {
		// 堆栈信息
		event.setStackTrace(Exceptions.getStackTraceAsString(error));
		event.setExceptionName(error.getClass().getSimpleName());
		event.setMessage(error.getMessage());
		StackTraceElement[] elements = error.getStackTrace();
		if (ObjectUtil.isNotEmpty(elements)) {
			// 报错的类信息
			StackTraceElement element = elements[0];
			event.setClassName(element.getClassName());
			event.setFileName(element.getFileName());
			event.setMethodName(element.getMethodName());
			event.setLineNumber(element.getLineNumber());
		}
	}

}
