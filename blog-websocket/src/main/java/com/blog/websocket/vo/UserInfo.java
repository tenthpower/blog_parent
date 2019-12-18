package com.blog.websocket.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -8820868230476089968L;

    private String sid;

    private String telNo;

    private String name;

    /**
     * 是否隐藏手机号
     */
    private Integer isHiddenTelNo;

    /**
     * 是否在线
     */
    private int isOnline;

}
