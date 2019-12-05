package com.blog.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChatInfoVo implements Serializable {

    private static final long serialVersionUID = -8820868230476089968L;

    private String sid;

    private String telNo;

    private String name;

    private String temp;

}
