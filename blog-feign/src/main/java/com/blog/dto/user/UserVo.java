package com.blog.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "登陆账号")
public class UserVo implements Serializable {

    private static final long serialVersionUID = -6012929095434378823L;

    @ApiModelProperty(value = "主键Id")
    private String id;

    @ApiModelProperty(value = "手机号")
    private String telNo;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "关注数")
    private int followCount;

    @ApiModelProperty(value = "粉丝数")
    private int fansCount;

    @ApiModelProperty(value = "在线时长")
    private Long onLine;

    @ApiModelProperty(value = "注册日期")
    private Date regTime;

    @ApiModelProperty(value = "更新日期")
    private Date updateTime;

    @ApiModelProperty(value = "最后登陆日期")
    private Date lastTime;

}
