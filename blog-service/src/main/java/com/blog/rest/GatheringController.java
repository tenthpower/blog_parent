package com.blog.rest;

import com.blog.dto.gathering.GatheringVo;
import com.blog.entity.Result;
import com.blog.entity.StatusCode;
import com.blog.service.GatheringService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Api(value="活动", tags = "GatheringController")
public class GatheringController {

    @Autowired
    private GatheringService gatheringService;

    /**
     * 查询全部
     * @return
     */
    @GetMapping(value="/gathering")
    @ApiOperation(value="查询全部")
    public Result findAll() throws Exception {
        return new Result(true, StatusCode.OK,"查询成功",gatheringService.findAll() );
    }
    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @GetMapping(value="/gathering/{id}")
    @ApiOperation(value="根据ID查询")
    public Result findById(@PathVariable String id){
        return new Result(true,StatusCode.OK,"查询成功",gatheringService.findById(id));
    }
    /**
     * 增加
     * @param
     * @return
     */
    @PostMapping(value="/gathering")
    @ApiOperation(value="增加")
    public Result add( @RequestBody GatheringVo gatheringVo){
        gatheringService.add(gatheringVo);
        return new Result(true,StatusCode.OK,"增加成功");
    }
    /**
     * 修改
     * @param
     * @return
     */
    @PutMapping(value="/gathering/{id}")
    @ApiOperation(value="修改")
    public Result update( @RequestBody GatheringVo gatheringVo,@PathVariable String id){
        gatheringVo.setId(id);
        gatheringService.update(gatheringVo);
        return new Result(true,StatusCode.OK,"修改成功");
    }
    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping(value="/gathering/{id}")
    @ApiOperation(value="删除")
    public Result deleteById(@PathVariable String id){
        gatheringService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }
}
