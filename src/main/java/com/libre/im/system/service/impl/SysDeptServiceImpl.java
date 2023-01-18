package com.libre.im.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.boot.autoconfigure.SpringContext;
import com.libre.im.system.mapper.SysDeptMapper;
import com.libre.im.system.pojo.dto.DeptCriteria;
import com.libre.im.system.pojo.entity.SysDept;
import com.libre.im.system.service.SysDeptService;
import com.libre.im.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhao.cheng
 */
@Service
@RequiredArgsConstructor
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

	@Override
	public Page<SysDept> findByPage(DeptCriteria param) {
		List<SysDept> deptList = this.list(getQueryWrapper(param));
		Page<SysDept> page = new Page<>(1, deptList.size());
		page.setRecords(deptList);
		return page;
	}

	@Override
	public List<SysDept> getSuperior(List<SysDept> deptList, List<SysDept> superiorList) {
		Set<Long> parentIds = deptList.stream().map(SysDept::getParentId).collect(Collectors.toSet());
		List<SysDept> result = super.listByIds(parentIds);
		if (CollectionUtils.isEmpty(result)) {
			return superiorList;
		}
		superiorList.addAll(result);
		return getSuperior(result, superiorList);
	}

	@Override
	public List<SysDept> getChildren(List<Long> deptIdList, List<SysDept> childrenList) {
		List<SysDept> result = getByParentId(deptIdList);
		if (result.isEmpty()) {
			return childrenList;
		}
		childrenList.addAll(result);
		List<Long> deptIdResult = result.stream().map(SysDept::getId).collect(Collectors.toList());
		return getChildren(deptIdResult, childrenList);
	}

	@Override
	public List<Long> findSuperiorIds(Long deptId) {
		List<Long> path = new LinkedList<>();
		findPath(deptId, path);
		Collections.reverse(path);
		return path;
	}

	@Override
	public boolean deleteIfUnusedByIds(Collection<Long> ids) {
		SysUserService userService = SpringContext.getBean(SysUserService.class);
		Assert.notNull(userService, "userService must not be null");
		// List<SysUser> userList = userService.findListByDeptIds(ids);
		// if (CollectionUtils.isEmpty(userList)) {
		// throw new BusinessException("存在用户岗位关系");
		// }
		return super.removeByIds(ids);
	}

	private Wrapper<SysDept> getQueryWrapper(DeptCriteria param) {
		LambdaQueryWrapper<SysDept> wrapper = new LambdaQueryWrapper<>();
		boolean blurryQuery = param.isBlurryQuery();

		wrapper.like(blurryQuery, SysDept::getDeptName, param.getBlurry());
		wrapper.eq(Objects.nonNull(param.getParentId()), SysDept::getParentId, param.getParentId());

		if (param.haveTime()) {
			wrapper.between(SysDept::getGmtCreate, param.getStartTime(), param.getEndTime());
		}
		return wrapper;
	}

	private void findPath(Long deptId, List<Long> path) {
		if (ObjectUtils.nullSafeEquals(0L, deptId)) {
			path.add(deptId);
			SysDept dept = getById(deptId);
			findPath(dept.getParentId(), path);
		}
	}

	private List<SysDept> getByParentId(List<Long> deptIdList) {
		if (CollectionUtils.isEmpty(deptIdList)) {
			return Collections.emptyList();
		}
		LambdaQueryWrapper<SysDept> wrapper = new LambdaQueryWrapper<>();
		wrapper.in(SysDept::getParentId, deptIdList);
		return list(wrapper);
	}

}
