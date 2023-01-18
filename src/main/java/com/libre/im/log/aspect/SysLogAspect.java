package com.libre.im.log.aspect;

import com.libre.im.common.security.dto.AuthUser;
import com.libre.im.common.security.support.SecurityUtil;
import com.libre.im.log.annotation.ApiLog;
import com.libre.im.log.support.*;
import com.libre.toolkit.json.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 操作日志使用spring event异步入库
 *
 * @author L.cm
 */
@Slf4j
@Order
@Aspect
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class SysLogAspect {

	private final ApplicationEventPublisher publisher;

	/**
	 * 环绕融资
	 * @param point ProceedingJoinPoint
	 * @param apiLog ApiLog
	 * @return Object
	 * @throws Throwable
	 */
	@Around("@annotation(apiLog)")
	public Object logAround(ProceedingJoinPoint point, ApiLog apiLog) throws Throwable {
		// 类和方法信息
		String strClassName = point.getTarget().getClass().getName();
		MethodSignature ms = (MethodSignature) point.getSignature();
		String strMethodName = ms.getName();
		log.info("[class]:{},[method]:{}", strClassName, strMethodName);
		SysLogEvent event = SysLogUtil.buildSysLogEvent(apiLog.type());
		event.setDescription(apiLog.value());
		event.setMethodName(strMethodName);
		event.setClassName(strClassName);
		event.setData(getPostJson(point, ms));
		// 执行时间
		long startNs = System.nanoTime();
		try {
			Object result = point.proceed();
			if (SysLogType.Login.equals(apiLog.type())) {
				AuthUser authUser = SecurityUtil.getUser();
				Optional.ofNullable(authUser).ifPresent(user -> {
					event.setUserId(authUser.getUserId());
					event.setUsername(authUser.getUsername());
				});
			}
			// 耗时
			event.setRequestTime(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs));
			event.setSuccess(SysLogConstant.SUCCESS);
			// 发送异步日志事件
			publisher.publishEvent(event);
			return result;
		}
		catch (Throwable e) {
			// 耗时
			event.setRequestTime(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs));
			// 异常详情
			SysLogUtil.initErrorInfo(e, event);
			event.setSuccess(SysLogConstant.FAILED);
			// 发送异步日志事件
			publisher.publishEvent(event);
			throw e;
		}
	}

	private static String getPostJson(ProceedingJoinPoint point, MethodSignature ms) {
		Object[] args = point.getArgs();
		Method method = ms.getMethod();
		// 一次请求只能有一个 request body
		Object requestBodyValue = null;
		for (int i = 0; i < args.length; i++) {
			// 读取方法参数
			MethodParameter methodParam = ReflectionUtil.getMethodParameter(method, i);
			RequestBody requestBody = methodParam.getParameterAnnotation(RequestBody.class);
			// 如果是body的json则是对象
			if (requestBody != null) {
				requestBodyValue = args[i];
				break;
			}
		}
		return requestBodyValue == null ? null : JsonUtil.toJson(requestBodyValue);
	}

}
