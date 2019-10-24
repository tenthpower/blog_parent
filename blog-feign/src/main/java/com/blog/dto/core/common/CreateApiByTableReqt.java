package com.blog.dto.core.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

@Data
@ApiModel(value = "通过表名生成增删改查API文件请求DTO")
public class CreateApiByTableReqt implements Serializable {

    @ApiModelProperty(value = "表名")
    private String tableName;

    @ApiModelProperty(value = "对应的对象名称")
    private String objectName;



}
