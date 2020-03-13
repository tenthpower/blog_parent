package com.blog.controller.dto;

import com.blog.websocket.vo.UserInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class LoginInResp implements Serializable {

    private String sid;

    private Integer publicCount;

    private Integer onlinePublicCount;

    private List<UserInfo> onlinePublicUsers = null;



    private Integer friendCount;

    private Integer onlineFriendCount;

    private List<UserInfo> friendUsers = null;

}
