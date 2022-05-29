package com.libre.im.admin.security.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author: Libre
 * @Date: 2022/5/29 11:43 PM
 */
@Data
@TableName("im_role")
public class SysRole {

    @TableId
    private Long id;

    private String roleName;


}
