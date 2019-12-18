package com.blog.util.vo;

import com.blog.websocket.vo.UserInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OnlineInfoVo implements Serializable {

    private long onlineCount;

    private long count;

    private List<UserInfo> userInfos;
}
