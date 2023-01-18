package com.libre.im.system.toolkit;

import com.libre.im.system.constant.MenuConstants;
import com.libre.im.system.pojo.SysMenu;
import com.libre.im.system.pojo.vo.MenuMetaVO;
import com.libre.im.system.pojo.vo.MenuVO;
import com.libre.toolkit.core.StringPool;
import com.libre.toolkit.core.StringUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 菜单处理过滤
 *
 * @author Libre
 * @date 2021/7/12 19:28
 */
public class MenuUtil {

	public static List<MenuVO> transformTree(List<SysMenu> menuList) {
		List<MenuVO> menuVOList = transformList(menuList);
		return menuVOList.stream().filter(menuVO -> menuVO.getParentId() == 0).peek(menuVO -> {
			List<MenuVO> children = getChildren(menuVO, menuVOList);
			if (CollectionUtils.isNotEmpty(children)) {
				menuVO.setChildren(children);
			}
		}).collect(Collectors.toList());
	}

	public static List<MenuVO> transformList(List<SysMenu> menuList) {
		List<MenuVO> menuVoList = new ArrayList<>();
		for (SysMenu menu : menuList) {
			String name = menu.getName();
			String path = menu.getPath();
			String title = menu.getTitle();
			String component = menu.getComponent();
			MenuVO menuVo = new MenuVO();
			menuVo.setId(menu.getId());
			menuVo.setParentId(menu.getParentId());
			menuVo.setName(StringUtil.isNotBlank(name) ? name : title);
			// 一级目录需要加斜杠，不然会报警告
			Long parentId = menu.getParentId();
			if (parentId == null && !path.startsWith("http")) {
				menuVo.setPath(StringPool.SLASH + path);
			}
			else {
				menuVo.setPath(path);
			}
			menuVo.setHidden(Boolean.FALSE);
			// 如果不是外链
			if (MenuConstants.IS_FRAME_NO.equals(menu.getIsFrame())) {
				if (parentId == null || parentId == 0) {
					menuVo.setComponent(StringUtil.isBlank(component) ? "Layout" : component);
				}
				else if (StringUtil.isNotBlank(component)) {
					menuVo.setComponent(component);
				}
			}
			menuVo.setMeta(new MenuMetaVO(title, menu.getIcon(), Boolean.TRUE));
			menuVoList.add(menuVo);
		}
		return menuVoList;
	}

	private static List<MenuVO> getChildren(MenuVO menuVO, List<MenuVO> menus) {
		return menus.stream().filter(item -> Objects.equals(item.getParentId(), menuVO.getId()))
				.peek(menu -> menu.setChildren(getChildren(menu, menus))).collect(Collectors.toList());
	}

}
