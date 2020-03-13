package com.blog.service;

import com.blog.controller.dto.LoginInReqt;
import com.blog.controller.dto.LoginInResp;
import com.blog.websocket.vo.UserInfo;

/**
 * 用户Service
 */
public interface UserInfoService {

    /**
     * 用户登陆
     */
    LoginInResp loginIn(LoginInReqt params) throws Exception;

    /**
     * 查询用户信息
     */
    UserInfo getUserInfo(UserInfo params) throws Exception;

    /**
     * 更新用户信息
     */
    UserInfo updateUserInfo(UserInfo params) throws Exception;
}
