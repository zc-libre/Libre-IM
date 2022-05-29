package com.libre.im.admin.security.vo;

import com.libre.im.admin.security.pojo.SysRole;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Libre
 * @date 2022/1/1 18:31
 */
@Data
public class UserVO {
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 部门ID
     */
    private Long deptId;
    /**
     * 角色信息 [{id: 2, name: "普通用户", level: 2, dataScope: "自定义"}, {id: 1, name: "超级管理员", level: 1, dataScope: "全部"}]
     */
    private List<SysRole> roles;
    /**
     * 用户账号
     */
    private String username;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 用户性别（0男 1女 2未知）
     */
    private Integer gender;
    /**
     * 头像地址
     */
    private String avatar;
    /**
     * 用户类型（0系统用户 1管理员）
     */
    private Boolean isAdmin;
    /**
     * 帐号状态（0停用 1正常）
     */
    private Boolean enabled;
    /**
     * 登录状态（0:正常 1:锁定）
     */
    private Boolean locked;
    /**
     * 删除标志（0代表存在 1代表删除）
     */
    private Boolean delFlag;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建者
     */
    private String gmtCreateName;
    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;
    /**
     * 更新者
     */
    private String gmtModifiedName;
    /**
     * 更新时间
     */
    private LocalDateTime gmtModified;
}
