package com.blog.rest;

import com.blog.dto.lable.LabelVo;
import com.blog.entity.PageResult;
import com.blog.entity.Result;
import com.blog.entity.StatusCode;
import com.blog.service.LabelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Api(value="标签", tags = "LabelController")
public class LabelController {

    @Autowired
    private LabelService labelService;

    /**
     * 查询全部
     * @return
     */
    @GetMapping(value="/label")
    @ApiOperation(value="查询全部")
    public Result findAll() throws Exception {
        return new Result(true, StatusCode.OK,"查询成功",labelService.findAll() );
    }
    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @GetMapping(value="/label/{id}")
    @ApiOperation(value="根据ID查询")
    public Result findById(@PathVariable String id){
        return new Result(true,StatusCode.OK,"查询成功",labelService.findById(id));
    }
    /**
     * 增加
     * @param
     * @return
     */
    @PostMapping(value="/label")
    @ApiOperation(value="增加")
    public Result add( @RequestBody LabelVo labelVo){
        labelService.add(labelVo);
        return new Result(true,StatusCode.OK,"增加成功");
    }
    /**
     * 修改
     * @param
     * @return
     */
    @PutMapping(value="/label/{id}")
    @ApiOperation(value="修改")
    public Result update( @RequestBody LabelVo labelVo,@PathVariable String id){
        labelVo.setId(id);
        labelService.update(labelVo);
        return new Result(true,StatusCode.OK,"修改成功");
    }
    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping(value="/label/{id}")
    @ApiOperation(value="删除")
    public Result deleteById(@PathVariable String id){
        labelService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /**
     * 根据条件查询
     * @param labelVo
     * @return
     */
    @PostMapping(value="/label/search")
    @ApiOperation(value="根据条件查询")
    public Result findSearch(@RequestBody LabelVo labelVo){
        return new Result(true,StatusCode.OK,"查询成功",labelService.findSearch(labelVo));
    }

    /**
     * 条件+分页查询
     * @param labelVo
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value="/label/search/{page}/{size}")
    @ApiOperation(value="条件+分页查询")
    public Result findSearch(@RequestBody LabelVo labelVo,@PathVariable int page, @PathVariable int size){
        Page pageList= labelService.findSearch(labelVo,page,size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<>(pageList.getTotalElements(),pageList.getContent()));
    }

}
