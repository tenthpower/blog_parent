package com.blog.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GroupChatInfoVo implements Serializable {

    private static final long serialVersionUID = -8820868321476089968L;

    private String groupId;

    private String groupName;

    private List<ChatInfoVo> chatInfoList = null;

    private String temp;



}
