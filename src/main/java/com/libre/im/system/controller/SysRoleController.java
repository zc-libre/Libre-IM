package com.libre.im.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.libre.im.log.annotation.ApiLog;
import com.libre.im.system.pojo.dto.RoleCriteria;
import com.libre.im.system.pojo.dto.RoleMenuDTO;
import com.libre.im.system.pojo.SysMenu;
import com.libre.im.system.pojo.entity.SysRole;
import com.libre.im.system.pojo.vo.RoleVO;
import com.libre.im.system.service.SysMenuService;
import com.libre.im.system.service.SysRoleService;
import com.libre.toolkit.result.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author: Libre
 * @Date: 2022/9/4 5:05 PM
 */
@RestController
@RequestMapping("/api/sys/role")
@RequiredArgsConstructor
public class SysRoleController {

	private final SysRoleService roleService;

	private final SysMenuService menuService;

	@ApiOperation("角色分页列表")
	@GetMapping("/page")
	public R<PageDTO<RoleVO>> page(Page<SysRole> page, RoleCriteria roleCriteria) {
		PageDTO<RoleVO> rolePage = roleService.findByPage(page, roleCriteria);
		return R.data(rolePage);
	}

	@GetMapping("/all")
	public R<List<SysRole>> all() {
		List<SysRole> list = roleService.list();
		return R.data(list);
	}

	@Operation(summary = "获取单个role")
	@GetMapping("/get/{id}")
	public R<SysRole> query(@PathVariable Long id) {
		return R.data(roleService.getById(id));
	}

	@Operation(summary = "获取角色菜单")
	@GetMapping("{id}/menus")
	public R<List<SysMenu>> getMenus(@NotNull @PathVariable("id") Long id) {
		return R.data(menuService.getListByRoleIds(Collections.singletonList(id)));
	}

	@ApiLog("修改角色菜单")
	@Operation(summary = "修改角色菜单")
	@PutMapping("/menu")
	public R<Boolean> updateMenu(@Validated @RequestBody RoleMenuDTO roleMenu) {
		SysRole role = roleService.getById(roleMenu.getId());
		boolean res = roleService.updateMenus(role, roleMenu.getMenuIds());
		return R.status(res);
	}

	@ApiLog("修改角色")
	@PostMapping("/edit")
	public R<Boolean> edit(@RequestBody SysRole sysRole) {
		boolean res = roleService.saveOrUpdate(sysRole);
		return R.status(res);
	}

	@ApiLog("删除角色")
	@Operation(summary = "删除角色")
	@DeleteMapping
	public R<Boolean> delete(@NotEmpty @RequestBody Set<Long> ids) {
		boolean res = roleService.deleteIfUnusedByIds(ids);
		return R.status(res);
	}

}
