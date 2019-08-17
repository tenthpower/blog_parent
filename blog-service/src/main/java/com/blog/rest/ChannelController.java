package com.blog.rest;

import com.blog.dto.article.ChannelVo;
import com.blog.entity.Result;
import com.blog.entity.StatusCode;
import com.blog.service.ChannelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Api(value="频道", tags = "ChannelController")
public class ChannelController {

    @Autowired
    private ChannelService channelService;

    /**
     * 查询全部
     * @return
     */
    @GetMapping(value="/channel")
    @ApiOperation(value="查询全部")
    public Result findAll() throws Exception {
        return new Result(true, StatusCode.OK,"查询成功",channelService.findAll() );
    }
    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @GetMapping(value="/channel/{id}")
    @ApiOperation(value="根据ID查询")
    public Result findById(@PathVariable String id){
        return new Result(true,StatusCode.OK,"查询成功",channelService.findById(id));
    }
    /**
     * 增加
     * @param
     * @return
     */
    @PostMapping(value="/channel")
    @ApiOperation(value="增加")
    public Result add( @RequestBody ChannelVo channelVo){
        channelService.add(channelVo);
        return new Result(true,StatusCode.OK,"增加成功");
    }
    /**
     * 修改
     * @param
     * @return
     */
    @PutMapping(value="/channel/{id}")
    @ApiOperation(value="修改")
    public Result update( @RequestBody ChannelVo channelVo,@PathVariable String id){
        channelVo.setId(id);
        channelService.update(channelVo);
        return new Result(true,StatusCode.OK,"修改成功");
    }
    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping(value="/channel/{id}")
    @ApiOperation(value="删除")
    public Result deleteById(@PathVariable String id){
        channelService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }
}
