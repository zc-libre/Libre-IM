package com.libre.im.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.libre.im.system.pojo.dto.DeptCriteria;
import com.libre.im.system.pojo.entity.SysDept;
import com.libre.im.system.service.SysDeptService;
import com.libre.toolkit.result.R;
import com.libre.toolkit.validation.CreateGroup;
import com.libre.toolkit.validation.UpdateGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Libre
 * @date 2022/2/2 15:35
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/api/sys/dept")
@RequiredArgsConstructor
public class SysDeptController {

	private final SysDeptService deptService;

	@ApiOperation("查询部门")
	@GetMapping
	public R<Page<SysDept>> query(DeptCriteria param) {
		Page<SysDept> page = deptService.findByPage(param);
		return R.data(page);
	}

	@ApiOperation("查询部门:根据ID获取同级与上级数据")
	@PostMapping("superior")
	public List<SysDept> getSuperior(@RequestBody List<Long> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return deptService.list();
		}
		List<SysDept> deptList = deptService.listByIds(ids);
		List<SysDept> superiorList = new ArrayList<>(deptList);
		deptService.getSuperior(deptList, superiorList);
		return superiorList;
	}

	@ApiOperation("新增部门")
	@PostMapping
	public void create(@Validated(CreateGroup.class) @RequestBody SysDept entity) {
		deptService.save(entity);
	}

	@ApiOperation("修改部门")
	@PutMapping
	public void update(@Validated(UpdateGroup.class) @RequestBody SysDept entity) {
		deptService.updateById(entity);
	}

	@ApiOperation("删除部门")
	@DeleteMapping
	public void delete(@NotEmpty @RequestBody Set<Long> ids) {
		deptService.deleteIfUnusedByIds(ids);
	}

}
