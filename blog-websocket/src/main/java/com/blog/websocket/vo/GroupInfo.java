package com.blog.websocket.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class GroupInfo implements Serializable {

    private static final long serialVersionUID = -8820868321476089968L;

    private String groupId;

    private String groupName;

    /**
     * 讨论组所属人
     */
    private String groupUserSid;

    /**
     * 讨论组人集合
     */
    private Set<String> userSIds;

}
