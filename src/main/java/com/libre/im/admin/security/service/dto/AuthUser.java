package com.libre.im.admin.security.service.dto;

import com.libre.core.toolkit.CollectionUtil;
import com.libre.im.admin.security.jwt.JwtUser;
import com.libre.im.admin.security.pojo.RoleInfo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
@Getter
@Setter
public class AuthUser extends User {

    private Long userId;

    private String nickName;

    private Integer gender;

    private Integer isAdmin;

    private String avatar;

    private String email;

    private String phone;

    private List<RoleInfo> roleList;


    public AuthUser(String username,
                    String password,
                    boolean enabled,
                    boolean accountNonLocked,
                    Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, true, true, accountNonLocked, authorities);
    }

    public JwtUser toJwtUser() {
        JwtUser jwtUser = new JwtUser();
        jwtUser.setId(this.getUserId());
        jwtUser.setUserName(this.getUsername());
        jwtUser.setNickName(this.getNickName());
        jwtUser.setGender(this.getGender());
        jwtUser.setAvatar(this.getAvatar());
        jwtUser.setEmail(this.getEmail());
        jwtUser.setPhone(this.getPhone());
        jwtUser.setRoles(this.getRoleList());
        if (CollectionUtil.isNotEmpty(this.getRoleList())) {
            jwtUser.setRoleList(this.getRoleList().stream().map(RoleInfo::getTitle).collect(Collectors.toList()));
        }
        return jwtUser;
    }

    public static AuthUser formUser(AuthUser user, String newPassword) {
        AuthUser authUser = new AuthUser(
                user.getUsername(),
                newPassword,
                user.isEnabled(),
                user.isAccountNonLocked(),
                user.getAuthorities()
        );
        authUser.setUserId(user.getUserId());
        authUser.setNickName(user.getNickName());
        authUser.setIsAdmin(user.getIsAdmin());
        authUser.setGender(user.getGender());
        authUser.setAvatar(user.getAvatar());
        authUser.setEmail(user.getEmail());
        authUser.setPhone(user.getPhone());
        authUser.setRoleList(user.getRoleList());
        return authUser;
    }

}
