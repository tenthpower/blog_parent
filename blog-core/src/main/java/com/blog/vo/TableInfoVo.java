package com.blog.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 表结构信息Vo
 */
@Data
public class TableInfoVo implements Serializable {

    @ApiModelProperty(value = "表的列名")
    private String field;

    @ApiModelProperty(value = "字段类型")
    private String type;

    @ApiModelProperty(value = "字段编码类型")
    private String collation;

    @ApiModelProperty(value = "是否可以为空")
    private boolean isNull;

    @ApiModelProperty(value = "key的类型：PRI...")
    private String key;

    @ApiModelProperty(value = "默认值")
    private String _default;

    @ApiModelProperty(value = "")
    private String extra;

    @ApiModelProperty(value = "特权")
    private String privileges;

    @ApiModelProperty(value = "备注")
    private String comment;

}
