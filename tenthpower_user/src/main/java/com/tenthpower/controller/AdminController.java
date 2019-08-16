package com.tenthpower.controller;

import com.tenthpower.dto.user.AdminVo;
import com.tenthpower.entity.Result;
import com.tenthpower.entity.StatusCode;
import com.tenthpower.pojo.Admin;
import com.tenthpower.service.AdminService;
import com.tenthpower.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@Api(value="职位", tags = "AdminController")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 查询全部
     * @return
     */
    @GetMapping(value="")
    @ApiOperation(value="查询全部")
    public Result findAll() throws Exception {
        return new Result(true, StatusCode.OK,"查询成功",adminService.findAll() );
    }
    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @GetMapping(value="/{id}")
    @ApiOperation(value="根据ID查询")
    public Result findById(@PathVariable String id){
        return new Result(true,StatusCode.OK,"查询成功",adminService.findById(id));
    }
    /**
     * 增加
     * @param
     * @return
     */
    @PostMapping(value="")
    @ApiOperation(value="增加")
    public Result add( @RequestBody AdminVo adminVo){
        adminService.add(adminVo);
        return new Result(true,StatusCode.OK,"增加成功");
    }
    /**
     * 修改
     * @param
     * @return
     */
    @PutMapping(value="/{id}")
    @ApiOperation(value="修改")
    public Result update( @RequestBody AdminVo adminVo,@PathVariable String id){
        adminVo.setId(id);
        adminService.update(adminVo);
        return new Result(true,StatusCode.OK,"修改成功");
    }
    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping(value="/{id}")
    @ApiOperation(value="删除")
    public Result deleteById(@PathVariable String id){
        adminService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /**
     * admin登陆
     * @param loginMap
     * @return
     */
    @PostMapping(value="/login")
    public Result login(@RequestBody Map<String,String> loginMap){
        AdminVo adminVo = adminService.findByLoginnameAndPassword(loginMap.get("loginname"), loginMap.get("password"));
        if(null != adminVo){
            // 生成token
            String token = jwtUtil.createJWT(adminVo.getId(), adminVo.getLoginname(), "admin");
            Map<String, Object> map=new HashMap();
            map.put("token",token);
            map.put("info",adminVo);
            return new Result(true,StatusCode.OK,"登陆成功", map);
        } else {
            return new Result(false,StatusCode.LOGINERROR,"用户名或密码错误");
        }
    }

}
