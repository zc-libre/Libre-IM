package com.libre.im.common.config;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.libre.toolkit.core.ObjectUtil;
import com.libre.toolkit.core.StringPool;
import com.libre.toolkit.core.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 解决 Mybatis Plus page SQL注入问题
 */
@Slf4j
public class PageArgumentResolver implements HandlerMethodArgumentResolver {

	private static final String ORDER_ASC = "asc";

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return Page.class.equals(parameter.getParameterType());
	}

	/**
	 * page 参数解析
	 * @param parameter MethodParameter
	 * @param mavContainer ModelAndViewContainer
	 * @param request NativeWebRequest
	 * @param binderFactory WebDataBinderFactory
	 * @return 检查后新的page对象
	 */
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest request, WebDataBinderFactory binderFactory) {
		// 分页参数 page: 0, size: 10, sort=id%2Cdesc
		String pageParam = request.getParameter("page");
		String sizeParam = request.getParameter("size");
		String[] sortParam = request.getParameterValues("sort");
		Page<?> page = new Page<>();
		if (StringUtil.isNotBlank(pageParam)) {
			page.setCurrent(Long.parseLong(pageParam));
		}
		if (StringUtil.isNotBlank(sizeParam)) {
			page.setSize(Long.parseLong(sizeParam));
		}
		if (ObjectUtil.isEmpty(sortParam)) {
			return page;
		}
		for (String param : sortParam) {
			if (StringUtil.isBlank(param)) {
				continue;
			}
			String[] split = param.split(StringPool.COMMA);
			// 清理字符串
			OrderItem orderItem = new OrderItem();
			orderItem.setColumn(cleanIdentifier(split[0]));
			orderItem.setAsc(isOrderAsc(split));
			page.addOrder(orderItem);
		}
		// 分页溢出的情况大于 long 最大值
		if (page.offset() < 0) {
			throw new IllegalArgumentException("Paging parameter exceeds the maximum.");
		}
		return page;
	}

	private static String cleanIdentifier(String param) {
		if (param == null) {
			return null;
		}
		StringBuilder paramBuilder = new StringBuilder();
		for (int i = 0; i < param.length(); i++) {
			char c = param.charAt(i);
			if (Character.isJavaIdentifierPart(c)) {
				paramBuilder.append(c);
			}
		}
		return paramBuilder.toString();
	}

	private static boolean isOrderAsc(String[] split) {
		// 默认 desc
		if (split.length < 2) {
			return false;
		}
		return ORDER_ASC.equalsIgnoreCase(split[1]);
	}

}
