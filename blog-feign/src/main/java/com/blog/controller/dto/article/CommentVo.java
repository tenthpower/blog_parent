package com.blog.controller.dto.article;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "专栏文章评论")
public class CommentVo implements Serializable {

    private static final long serialVersionUID = 8966255823613417273L;

    @ApiModelProperty(value = "主键Id")
    private String _id;

    @ApiModelProperty(value = "文章ID")
    private String articleId;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "评论人ID")
    private String userId;

    @ApiModelProperty(value = "评论ID，如果为0000--00000表示文章的顶级评论")
    private String parentId;

    @ApiModelProperty(value = "评论日期")
    private Date publishDate;

}
