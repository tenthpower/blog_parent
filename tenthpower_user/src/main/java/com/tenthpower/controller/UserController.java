package com.tenthpower.controller;

import com.tenthpower.dto.user.AdminVo;
import com.tenthpower.dto.user.UserVo;
import com.tenthpower.entity.Result;
import com.tenthpower.entity.StatusCode;
import com.tenthpower.service.AdminService;
import com.tenthpower.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Api(value="用户", tags = "UserController")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     * @param
     * @return
     */
    @PostMapping(value="register/{code}")
    @ApiOperation(value="增加")
    public Result add( @RequestBody UserVo userVo, @PathVariable String code){
        userService.add(userVo, code);
        return new Result(true,StatusCode.OK,"注册成功");
    }
}
