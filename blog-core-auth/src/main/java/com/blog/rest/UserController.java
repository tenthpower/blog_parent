package com.blog.rest;

import com.blog.dto.user.UserVo;
import com.blog.entity.Result;
import com.blog.entity.StatusCode;
import com.blog.service.UserService;
import com.blog.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Api(value="UserController", tags = "UserController")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 用户注册
     * @param
     * @return
     */
    @PostMapping(value="/user/register/{code}")
    @ApiOperation(value="增加")
    public Result add( @RequestBody UserVo userVo, @PathVariable String code){
        userService.add(userVo, code);
        return new Result(true,StatusCode.OK,"注册成功");
    }

    /**
     * 用户登陆
     * @param loginMap
     * @return
     */
    @PostMapping(value="/user/login")
    public Result login(@RequestBody Map<String,String> loginMap){
        UserVo userVo = userService.findByTelNoAndPassword(loginMap.get("telNo"), loginMap.get("password"));
        if(null != userVo){
            // 生成token
            String token = jwtUtil.createJWT(userVo.getId(), userVo.getTelNo(), "user");
            Map map=new HashMap();
            map.put("token",token);
            map.put("info",userVo);
            return new Result(true,StatusCode.OK,"登陆成功", map);
        }else{
            return new Result(false,StatusCode.LOGINERROR,"用户名或密码错误");
        }
    }
}
