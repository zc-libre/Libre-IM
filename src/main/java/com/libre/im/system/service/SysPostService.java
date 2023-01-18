package com.libre.im.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.im.system.pojo.entity.SysPost;

import java.util.List;

/**
 * @author zhao.cheng
 */
public interface SysPostService extends IService<SysPost> {

	/**
	 * 查找用户岗位信息
	 * @param userId 用户id
	 * @return 集合
	 */
	List<SysPost> getListByUserId(Long userId);

}
