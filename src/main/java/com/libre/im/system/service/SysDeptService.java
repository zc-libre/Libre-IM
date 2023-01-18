package com.libre.im.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.im.system.pojo.dto.DeptCriteria;
import com.libre.im.system.pojo.entity.SysDept;

import java.util.Collection;
import java.util.List;

/**
 * @author zhao.cheng
 */
public interface SysDeptService extends IService<SysDept> {

	/**
	 * 获取所有的父类
	 * @param deptList deptList
	 * @param superiorList 父类
	 * @return deptList
	 */
	List<SysDept> getSuperior(List<SysDept> deptList, List<SysDept> superiorList);

	/**
	 * 获取父级路径ids
	 * @param deptId /
	 * @return /
	 */
	List<Long> findSuperiorIds(Long deptId);

	/**
	 * 获取所有的子部门
	 * @param deptIdList 部门id列表
	 * @param childrenList 子部门
	 * @return deptList
	 */
	List<SysDept> getChildren(List<Long> deptIdList, List<SysDept> childrenList);

	/**
	 * 如果没用使用时删除
	 * @param ids id集合
	 * @return 是否成功
	 */
	boolean deleteIfUnusedByIds(Collection<Long> ids);

	/**
	 * 分页查询
	 * @param param /
	 * @return /
	 */
	Page<SysDept> findByPage(DeptCriteria param);

}
