package com.blog.dto;

import com.blog.websocket.vo.UserInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class LoginInResp implements Serializable {

    private String sid;

    private Integer onlineFriendCount;

    private List<UserInfo> friendUsers = null;

}
