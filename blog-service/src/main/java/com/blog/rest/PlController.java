package com.blog.rest;

import com.blog.dto.qa.PlVo;
import com.blog.entity.Result;
import com.blog.entity.StatusCode;
import com.blog.service.PlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Api(value="标签", tags = "PlController")
public class PlController {

    @Autowired
    private PlService plService;

    /**
     * 查询全部
     * @return
     */
    @GetMapping(value="/pl")
    @ApiOperation(value="查询全部")
    public Result findAll() throws Exception {
        return new Result(true, StatusCode.OK,"查询成功",plService.findAll() );
    }
    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @GetMapping(value="/pl/{id}")
    @ApiOperation(value="根据ID查询")
    public Result findById(@PathVariable String id){
        return new Result(true,StatusCode.OK,"查询成功",plService.findById(id));
    }
    /**
     * 增加
     * @param
     * @return
     */
    @PostMapping(value="/pl")
    @ApiOperation(value="增加")
    public Result add( @RequestBody PlVo plVo){
        plService.add(plVo);
        return new Result(true,StatusCode.OK,"增加成功");
    }
    /**
     * 修改
     * @param
     * @return
     */
    @PutMapping(value="/pl/{id}")
    @ApiOperation(value="修改")
    public Result update( @RequestBody PlVo plVo,@PathVariable String id){
        plVo.setId(id);
        plService.update(plVo);
        return new Result(true,StatusCode.OK,"修改成功");
    }
    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping(value="/pl/{id}")
    @ApiOperation(value="删除")
    public Result deleteById(@PathVariable String id){
        plService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }
}
