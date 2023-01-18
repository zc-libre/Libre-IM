package com.libre.im.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.libre.im.common.security.dto.AuthUser;
import com.libre.im.log.annotation.ApiLog;
import com.libre.im.system.pojo.dto.MenuCriteria;
import com.libre.im.system.pojo.dto.MenuDTO;
import com.libre.im.system.pojo.SysMenu;
import com.libre.im.system.pojo.vo.MenuVO;
import com.libre.im.system.service.SysMenuService;
import com.libre.im.system.service.mapstruct.SysMenuMapping;
import com.libre.im.system.toolkit.MenuUtil;
import com.libre.toolkit.result.R;
import com.libre.toolkit.validation.CreateGroup;
import com.libre.toolkit.validation.UpdateGroup;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: Libre
 * @Date: 2022/9/11 4:23 PM
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sys/menu")
public class SysMenuController {

	private final SysMenuService menuService;

	@GetMapping("all")
	public R<List<SysMenu>> all() {
		List<SysMenu> list = menuService.list();
		return R.data(list);
	}

	@GetMapping
	public R<PageDTO<SysMenu>> list(MenuCriteria criteria) {
		PageDTO<SysMenu> page = menuService.findByPage(criteria);
		return R.data(page);
	}

	@GetMapping("/role-menus")
	public R<List<SysMenu>> roleMenus(AuthUser user) {
		List<SysMenu> menuList = menuService.getMenuListByUsername(user.getUsername());
		return R.data(menuList);
	}

	@Operation(summary = "查询菜单:根据ID获取同级与上级数据")
	@PostMapping("superior")
	public R<List<SysMenu>> getSuperior(@RequestBody List<Long> ids) {
		List<SysMenu> menuList = menuService.getSuperior(ids);
		return R.data(menuList);
	}

	@GetMapping("tree")
	public R<List<MenuVO>> tree() {
		List<SysMenu> menuList = menuService.list();
		List<MenuVO> vos = MenuUtil.transformTree(menuList);
		return R.data(vos);
	}

	@ApiLog("新增菜单")
	@Operation(summary = "新增菜单")
	@PostMapping
	@PreAuthorize("@sec.hasPermission('system:menu:add')")
	public R<Boolean> create(@Validated(CreateGroup.class) @RequestBody MenuDTO menuDTO) {
		SysMenuMapping mapping = SysMenuMapping.INSTANCE;
		SysMenu sysMenu = mapping.sourceToTarget(menuDTO);
		menuService.save(sysMenu);
		return R.status(true);
	}

	@ApiLog("修改菜单")
	@Operation(summary = "修改菜单")
	@PutMapping
	@PreAuthorize("@sec.hasPermission('system:menu:edit')")
	public R<Boolean> update(@Validated(UpdateGroup.class) @RequestBody MenuDTO menuDTO) {
		SysMenuMapping mapping = SysMenuMapping.INSTANCE;
		SysMenu sysMenu = mapping.sourceToTarget(menuDTO);
		menuService.updateById(sysMenu);
		return R.status(true);
	}

	@ApiLog("菜单删除")
	@DeleteMapping
	public R<Boolean> deleteByIds(@RequestBody List<Long> ids) {
		menuService.deleteByIds(ids);
		return R.status(true);
	}

}
