package com.blog.rest;

import com.blog.dto.gathering.UsergathVo;
import com.blog.entity.Result;
import com.blog.entity.StatusCode;
import com.blog.service.UsergathService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Api(value="活动点击记录", tags = "UsergathController")
public class UsergathController {

    @Autowired
    private UsergathService usergathService;

    /**
     * 查询全部
     * @return
     */
    @GetMapping(value="/usergath")
    @ApiOperation(value="查询全部")
    public Result findAll() throws Exception {
        return new Result(true, StatusCode.OK,"查询成功",usergathService.findAll() );
    }
    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @GetMapping(value="/usergath/{id}")
    @ApiOperation(value="根据ID查询")
    public Result findById(@PathVariable String id){
        return new Result(true,StatusCode.OK,"查询成功",usergathService.findById(id));
    }
    /**
     * 增加
     * @param
     * @return
     */
    @PostMapping(value="/usergath")
    @ApiOperation(value="增加")
    public Result add( @RequestBody UsergathVo usergathVo){
        usergathService.add(usergathVo);
        return new Result(true,StatusCode.OK,"增加成功");
    }
    /**
     * 修改
     * @param
     * @return
     */
    @PutMapping(value="/usergath/{id}")
    @ApiOperation(value="修改")
    public Result update( @RequestBody UsergathVo usergathVo,@PathVariable String id){
        usergathVo.setId(id);
        usergathService.update(usergathVo);
        return new Result(true,StatusCode.OK,"修改成功");
    }
    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping(value="/usergath/{id}")
    @ApiOperation(value="删除")
    public Result deleteById(@PathVariable String id){
        usergathService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }
}
