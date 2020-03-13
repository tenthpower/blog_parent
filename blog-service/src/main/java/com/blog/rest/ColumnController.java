package com.blog.rest;

import com.blog.controller.dto.article.ColumnVo;
import com.blog.entity.Result;
import com.blog.entity.StatusCode;
import com.blog.service.ColumnService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Api(value="专栏", tags = "ColumnController")
public class ColumnController {

    @Autowired
    private ColumnService columnService;

    /**
     * 查询全部
     * @return
     */
    @GetMapping(value="/column")
    @ApiOperation(value="查询全部")
    public Result findAll() throws Exception {
        return new Result(true, StatusCode.OK,"查询成功",columnService.findAll() );
    }
    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @GetMapping(value="/column/{id}")
    @ApiOperation(value="根据ID查询")
    public Result findById(@PathVariable String id){
        return new Result(true,StatusCode.OK,"查询成功",columnService.findById(id));
    }
    /**
     * 增加
     * @param
     * @return
     */
    @PostMapping(value="/column")
    @ApiOperation(value="增加")
    public Result add( @RequestBody ColumnVo columnVo){
        columnService.add(columnVo);
        return new Result(true,StatusCode.OK,"增加成功");
    }
    /**
     * 修改
     * @param
     * @return
     */
    @PutMapping(value="/column/{id}")
    @ApiOperation(value="修改")
    public Result update( @RequestBody ColumnVo columnVo,@PathVariable String id){
        columnVo.setId(id);
        columnService.update(columnVo);
        return new Result(true,StatusCode.OK,"修改成功");
    }
    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping(value="/column/{id}")
    @ApiOperation(value="删除")
    public Result deleteById(@PathVariable String id){
        columnService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }
}
