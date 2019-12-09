package com.blog.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OnLineInfoVo implements Serializable {

    private static final long serialVersionUID = -8820868230476089968L;

    private Integer chatCount;

    private Integer groupCount;

    private List<ChatInfoVo> chatInfoList;

}
