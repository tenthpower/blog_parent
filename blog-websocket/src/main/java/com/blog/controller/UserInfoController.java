package com.blog.controller;

import com.blog.controller.dto.LoginInReqt;
import com.blog.controller.dto.LoginInResp;
import com.blog.entity.Result;
import com.blog.entity.StatusCode;
import com.blog.service.UserInfoService;
import com.blog.websocket.vo.UserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@Api(value="用户", tags = "UserInfoController")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 登陆聊天。 目前为独立的服务
     *      进行登陆，验证是否用户存在数据库，如果不存在数据，则创建用户信息。
     * @param params
     * @return
     * @throws Exception
     */
    @PostMapping(value="/loginIn")
    @ApiOperation(value="进行登陆")
    public Result sendChatMessage(LoginInReqt params) throws Exception {
        LoginInResp resultData = userInfoService.loginIn(params);
        return new Result(true, StatusCode.OK, "登陆成功！", resultData);
    }

    /**
     * 查询用户信息
     */
    @PostMapping(value="/getInfo")
    @ApiOperation(value="查询用户信息")
    public Result getUserInfo(UserInfo params) throws Exception {
        UserInfo resultData = userInfoService.getUserInfo(params);
        return new Result(true, StatusCode.OK, "查询用户信息成功！", resultData);
    }

    /**
     * 更新用户信息
     */
    @PutMapping(value="/updateUserInfo")
    @ApiOperation(value="更新用户信息")
    public Result updateUserInfo(UserInfo params) throws Exception {
        UserInfo resultData = userInfoService.updateUserInfo(params);
        return new Result(true, StatusCode.OK, "更新用户信息成功！", resultData);
    }


}
