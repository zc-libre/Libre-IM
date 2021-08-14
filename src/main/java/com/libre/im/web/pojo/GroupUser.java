package com.libre.im.web.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 * @author ZC
 * @date 2021/8/14 20:52
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GroupUser extends BaseEntity{

    private String groupName;

    private Long groupAdmin;

    @TableField(exist = false)
    private Set<GroupUser> groupUsers;
}
