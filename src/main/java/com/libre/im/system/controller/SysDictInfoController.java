package com.libre.im.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.libre.im.log.annotation.ApiLog;
import com.libre.im.system.pojo.dto.DictInfoCriteria;
import com.libre.im.system.pojo.entity.SysDictInfo;
import com.libre.im.system.service.SysDictInfoService;
import com.libre.toolkit.result.R;
import com.libre.toolkit.validation.CreateGroup;
import com.libre.toolkit.validation.UpdateGroup;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 字典详情表 前端控制器
 * </p>
 *
 * @author L.cm
 * @since 2020-05-05
 */
@Validated
@RestController
@RequestMapping("/api/sys/dict/info")
@Tag(name = "系统：字典详情管理")
@RequiredArgsConstructor
public class SysDictInfoController {

	private final SysDictInfoService dictInfoService;

	@ApiLog("查询字典详情")
	@Operation(summary = "查询字典详情")
	@GetMapping("all")
	public R<List<SysDictInfo>> queryAll() {
		return R.data(dictInfoService.list());
	}

	@ApiLog("查询字典详情")
	@Operation(summary = "查询字典详情")
	@GetMapping
	public R<IPage<SysDictInfo>> query(PageDTO<SysDictInfo> page, DictInfoCriteria query) {
		Page<SysDictInfo> dictInfoPage = dictInfoService.page(page, dictInfoService.getQueryWrapper(query));
		return R.data(dictInfoPage);
	}

	@ApiLog("查询多个字典详情")
	@Operation(summary = "查询多个字典详情")
	@GetMapping("map")
	public R<Map<String, List<SysDictInfo>>> getDictMaps(@RequestParam List<String> names) {
		if (names == null || names.isEmpty()) {
			return R.data(Collections.emptyMap());
		}
		LambdaQueryWrapper<SysDictInfo> wrapper = Wrappers.lambdaQuery();
		wrapper.in(SysDictInfo::getType, names);
		return R.data(dictInfoService.list(wrapper).stream().collect(Collectors.groupingBy(SysDictInfo::getType)));
	}

	@ApiLog("新增字典详情")
	@Operation(summary = "新增字典详情")
	@PostMapping
	public R<Boolean> create(@Validated(CreateGroup.class) @RequestBody SysDictInfo entity) {
		dictInfoService.save(entity);
		return R.status(true);
	}

	@ApiLog("修改字典详情")
	@Operation(summary = "修改字典详情")
	@PutMapping
	public R<Boolean> update(@Validated(UpdateGroup.class) @RequestBody SysDictInfo entity) {
		dictInfoService.updateById(entity);
		return R.status(true);
	}

	@ApiLog("删除字典详情")
	@Operation(summary = "删除字典详情")
	@DeleteMapping("{id}")
	public R<Boolean> delete(@NotNull @PathVariable Long id) {
		dictInfoService.removeById(id);
		return R.status(true);
	}

}
