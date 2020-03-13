package com.blog.rest;

import com.blog.controller.dto.lable.CityVo;
import com.blog.entity.Result;
import com.blog.entity.StatusCode;
import com.blog.service.CityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Api(value="标签", tags = "CityController")
public class CityController {

    @Autowired
    private CityService cityService;

    /**
     * 查询全部
     * @return
     */
    @GetMapping(value="/city")
    @ApiOperation(value="查询全部")
    public Result findAll() throws Exception {
        return new Result(true, StatusCode.OK,"查询成功",cityService.findAll() );
    }
    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @GetMapping(value="/city/{id}")
    @ApiOperation(value="根据ID查询")
    public Result findById(@PathVariable String id){
        return new Result(true,StatusCode.OK,"查询成功",cityService.findById(id));
    }
    /**
     * 增加
     * @param
     * @return
     */
    @PostMapping(value="/city")
    @ApiOperation(value="增加")
    public Result add( @RequestBody CityVo cityVo){
        cityService.add(cityVo);
        return new Result(true,StatusCode.OK,"增加成功");
    }
    /**
     * 修改
     * @param
     * @return
     */
    @PutMapping(value="/city/{id}")
    @ApiOperation(value="修改")
    public Result update( @RequestBody CityVo cityVo,@PathVariable String id){
        cityVo.setId(id);
        cityService.update(cityVo);
        return new Result(true,StatusCode.OK,"修改成功");
    }
    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping(value="/city/{id}")
    @ApiOperation(value="删除")
    public Result deleteById(@PathVariable String id){
        cityService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

}
