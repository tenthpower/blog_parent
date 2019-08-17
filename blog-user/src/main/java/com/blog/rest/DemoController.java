package com.blog.rest;

import com.blog.dto.user.AdminVo;
import com.blog.entity.Result;
import com.blog.entity.StatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
@Api(value="demoController", tags = "DemoController")
public class DemoController {


    /**
     * 模拟获取用户信息
     * @return
     */
    @GetMapping(value="/api/user/info")
    @ApiOperation(value="用户信息")
    public Result info(){
        Result result = new Result(true,StatusCode.OK,"用户信息");
        Map<String,Object> data = new HashMap<String,Object>();
        data.put("roles",new String[]{"admin"});
        data.put("role",new String[]{"admin"});
        data.put("name","admin");
        data.put("avatar","https://wpimg.wallstcn.com/f778738c‐e4f8‐4870‐b634-56703b4acafe.gif");
        result.setData(data);
        return result;
    }

    /**
     * 模拟获取用户信息
     * @param adminVo
     * @return
     */
    @PostMapping(value="/api/user/logout")
    @ApiOperation(value="用户信息")
    public Result logout(@RequestBody AdminVo adminVo){
        Result result = new Result(true,StatusCode.OK,"用户信息");
        Map<String,Object> data = new HashMap<String,Object>();
        data.put("roles",new String[]{"admin"});
        data.put("role",new String[]{"admin"});
        data.put("name","admin");
        data.put("avatar","https://wpimg.wallstcn.com/f778738c‐e4f8‐4870‐b634-56703b4acafe.gif");
        result.setData(data);
        return result;
    }
}
