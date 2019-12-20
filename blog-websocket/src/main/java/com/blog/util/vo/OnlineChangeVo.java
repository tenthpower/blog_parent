package com.blog.util.vo;

import com.blog.websocket.vo.UserInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 变动
 */
@Data
public class OnlineChangeVo implements Serializable {

    private UserInfo userInfo;

}
