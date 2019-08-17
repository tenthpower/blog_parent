package com.blog.rest;

import com.blog.dto.lable.UlVo;
import com.blog.entity.Result;
import com.blog.entity.StatusCode;
import com.blog.service.UlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Api(value="标签", tags = "UlController")
public class UlController {

    @Autowired
    private UlService ulService;

    /**
     * 查询全部
     * @return
     */
    @GetMapping(value="/ul")
    @ApiOperation(value="查询全部")
    public Result findAll() throws Exception {
        return new Result(true, StatusCode.OK,"查询成功",ulService.findAll() );
    }
    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @GetMapping(value="/ul/{id}")
    @ApiOperation(value="根据ID查询")
    public Result findById(@PathVariable String id){
        return new Result(true,StatusCode.OK,"查询成功",ulService.findById(id));
    }
    /**
     * 增加
     * @param
     * @return
     */
    @PostMapping(value="/ul")
    @ApiOperation(value="增加")
    public Result add( @RequestBody UlVo ulVo){
        ulService.add(ulVo);
        return new Result(true,StatusCode.OK,"增加成功");
    }
    /**
     * 修改
     * @param
     * @return
     */
    @PutMapping(value="/ul/{id}")
    @ApiOperation(value="修改")
    public Result update(@RequestBody UlVo ulVo, @PathVariable String id){
        ulVo.setId(id);
        ulService.update(ulVo);
        return new Result(true,StatusCode.OK,"修改成功");
    }
    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping(value="/ul/{id}")
    @ApiOperation(value="删除")
    public Result deleteById(@PathVariable String id){
        ulService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }
}
