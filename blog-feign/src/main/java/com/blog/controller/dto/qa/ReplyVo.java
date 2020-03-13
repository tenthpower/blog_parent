package com.blog.controller.dto.qa;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ReplyVo implements Serializable {

    private static final long serialVersionUID = -6541293430290978823L;

    @ApiModelProperty(value = "主键Id")
    private String id;

    @ApiModelProperty(value = "问题ID")
    private String problemId;

    @ApiModelProperty(value = "回答内容")
    private String content;

    @ApiModelProperty(value = "回答日期")
    private Date createTime;

    @ApiModelProperty(value = "更新日期")
    private Date updateTime;

    @ApiModelProperty(value = "回答人ID")
    private String userId;

    @ApiModelProperty(value = "回答人昵称")
    private String nickName;
}
