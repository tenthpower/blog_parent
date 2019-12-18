package com.blog.websocket.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class FriendInfo implements Serializable {

    private static final long serialVersionUID = -8820868212476089968L;

    /**
     * 好友id
     */
    private String fId;

    private String aSid;

    private String bSid;

}
