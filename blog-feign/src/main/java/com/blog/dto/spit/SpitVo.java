package com.blog.dto.spit;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "吐槽")
public class SpitVo implements Serializable {

    private static final long serialVersionUID = 1658489630726972196L;

    @ApiModelProperty(value = "主键Id")
    private String _id;

    @ApiModelProperty(value = "吐槽内容")
    private String content;

    @ApiModelProperty(value = "发布日期")
    private Date publishTime;

    @ApiModelProperty(value = "发布人ID")
    private String userId;

    @ApiModelProperty(value = "发布人昵称")
    private String nickName;

    @ApiModelProperty(value = "浏览量")
    private Integer visits;

    @ApiModelProperty(value = "点赞数")
    private Integer tHumBup;

    @ApiModelProperty(value = "分享数")
    private Integer share;

    @ApiModelProperty(value = "回复数")
    private Integer comment;

    @ApiModelProperty(value = "是否可见")
    private Integer state;

    @ApiModelProperty(value = "上级ID ")
    private String parentId;
}
