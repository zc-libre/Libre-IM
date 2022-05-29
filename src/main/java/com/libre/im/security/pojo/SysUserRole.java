package com.libre.im.security.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author: Libre
 * @Date: 2022/5/30 12:08 AM
 */
@Data
@TableName("im_user_role")
public class SysUserRole {

    private Long id;

    private Long userId;

    private Long roleId;
}
