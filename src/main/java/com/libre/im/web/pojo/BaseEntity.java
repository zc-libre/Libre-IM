package com.libre.im.web.pojo;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.libre.core.validation.CreateGroup;
import com.libre.core.validation.UpdateGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Null;
import java.time.LocalDateTime;

/**
 * @author Libre
 */
@Data
public class BaseEntity {
    /**
     * 主键id
     */
    @TableId
    @ApiModelProperty(value="主键id")
    @Null(groups = CreateGroup.class)
    @NotNull(groups = UpdateGroup.class)
    private Long id;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    @ApiModelProperty(value="创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    @ApiModelProperty(value="更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;
    /**
     * 创建人id
     */
    @ApiModelProperty(value="创建人id")
    @TableField(fill = FieldFill.INSERT)
    private Long gmtCreateId;
    /**
     * 创建人
     */
    @ApiModelProperty(value="创建人")
    @TableField(fill = FieldFill.INSERT)
    private String gmtCreateName;
    /**
     * 更新人id
     */
    @ApiModelProperty(value="更新人id")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long gmtModifiedId;
    /**
     * 更新人
     */
    @ApiModelProperty(value="更新人")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String gmtModifiedName;

    /**
     * 0删除 1未删除
     */
    @TableLogic(value = "0", delval = "1")
    @ApiModelProperty(value="1删除 0未删除")
    private Integer isDeleted;

}
