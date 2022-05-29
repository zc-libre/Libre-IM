package com.libre.im.security.utils;

import com.libre.core.toolkit.RequestUtils;
import com.libre.im.security.pojo.dto.AuthUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Libre
 * @date 2021/7/12 14:53
 */
public class SecurityUtil {

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取用户
     */
    public static AuthUser getUser(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof AuthUser) {
            return ((AuthUser) principal);
        }
        return null;
    }

    /**
     * 获取用户
     */
    public static AuthUser getUser() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }
        return getUser(authentication);
    }

    /**
     * 获取用户名
     */
    public static String getUserName() {
        AuthUser authUser = getUser();
        return authUser == null ? null : authUser.getUsername();
    }

    /**
     * 退出
     */
    public static void logout() {
        HttpServletRequest request = RequestUtils.getRequest();
        new SecurityContextLogoutHandler().logout(request, null, null);
    }
}
