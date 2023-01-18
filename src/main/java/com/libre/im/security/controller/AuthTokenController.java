package com.libre.im.security.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.libre.im.security.pojo.vo.TokenVo;
import com.libre.im.security.jwt.JwtTokenStore;
import com.libre.toolkit.result.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

/**
 * 认证 token 管理
 *
 * @author L.cm
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/token")
@Tag(name = "系统：token管理")
public class AuthTokenController {

	private final JwtTokenStore tokenStore;

	// @Operation(summary = "导出数据")
	// @GetMapping("/download")
	// @ResponseExcel(name = "认证token")
	// @PreAuthorize("@sec.isAuthenticated()")
	// public List<T> download(String filter) {
	// return tokenStore.getAll(filter);
	// }

	@Operation(summary = "查询列表")
	@GetMapping
	@PreAuthorize("@sec.isAuthenticated()")
	public R<Page<TokenVo>> query(PageDTO<TokenVo> page, String filter) {
		return R.data(tokenStore.page(page, filter));
	}

	@Operation(summary = "踢出用户")
	@DeleteMapping
	@PreAuthorize("@sec.isAdmin()")
	public R<Boolean> delete(@NotEmpty @RequestBody Set<String> keys) {
		tokenStore.remove(keys);
		return R.status(true);
	}

}
