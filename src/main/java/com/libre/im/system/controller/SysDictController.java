package com.libre.im.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.libre.im.log.annotation.ApiLog;
import com.libre.im.system.pojo.dto.DictCriteria;
import com.libre.im.system.pojo.entity.SysDict;
import com.libre.im.system.service.SysDictService;
import com.libre.toolkit.result.R;
import com.libre.toolkit.validation.CreateGroup;
import com.libre.toolkit.validation.UpdateGroup;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

/**
 * <p>
 * 字典 前端控制器
 * </p>
 *
 * @author L.cm
 * @since 2020-05-05
 */
@Validated
@RestController
@RequestMapping("/api/sys/dict")
@Tag(name = "系统：字典管理")
@RequiredArgsConstructor
public class SysDictController {

	private final SysDictService dictService;

	@ApiLog("查询字典")
	@Operation(summary = "查询字典")
	@GetMapping
	public R<IPage<SysDict>> query(Page<SysDict> page, DictCriteria query) {
		Page<SysDict> dictPage = dictService.page(page, dictService.getQueryWrapper(query));
		return R.data(dictPage);
	}

	@ApiLog("新增字典")
	@Operation(summary = "新增字典")
	@PostMapping
	@PreAuthorize("@sec.hasPermission('system:dict:add')")
	public R<Boolean> create(@Validated(CreateGroup.class) @RequestBody SysDict entity) {
		dictService.save(entity);
		return R.status(true);
	}

	@ApiLog("修改字典")
	@Operation(summary = "修改字典")
	@PutMapping
	@PreAuthorize("@sec.hasPermission('system:dict:edit')")
	public R<Boolean> update(@Validated(UpdateGroup.class) @RequestBody SysDict entity) {
		dictService.updateById(entity);
		return R.status(true);
	}

	@ApiLog("删除字典")
	@Operation(summary = "删除字典")
	@DeleteMapping
	@PreAuthorize("@sec.hasPermission('system:dict:del')")
	public R<Boolean> delete(@NotEmpty @RequestBody Set<Long> ids) {
		dictService.deleteIfUnusedByIds(ids);
		return R.status(true);
	}

}
