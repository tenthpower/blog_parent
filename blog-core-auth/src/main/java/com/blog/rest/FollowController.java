package com.blog.rest;

import com.blog.dto.user.FollowVo;
import com.blog.entity.Result;
import com.blog.entity.StatusCode;
import com.blog.service.FollowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Api(value="职位", tags = "FollowController")
public class FollowController {

    @Autowired
    private FollowService followService;

    /**
     * 查询全部
     * @return
     */
    @GetMapping(value="/follow")
    @ApiOperation(value="查询全部")
    public Result findAll() throws Exception {
        return new Result(true, StatusCode.OK,"查询成功",followService.findAll() );
    }
    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @GetMapping(value="/follow/{id}")
    @ApiOperation(value="根据ID查询")
    public Result findById(@PathVariable String id){
        return new Result(true,StatusCode.OK,"查询成功",followService.findById(id));
    }
    /**
     * 增加
     * @param
     * @return
     */
    @PostMapping(value="/follow")
    @ApiOperation(value="增加")
    public Result add( @RequestBody FollowVo followVo){
        followService.add(followVo);
        return new Result(true,StatusCode.OK,"增加成功");
    }
    /**
     * 修改
     * @param
     * @return
     */
    @PutMapping(value="/follow/{id}")
    @ApiOperation(value="修改")
    public Result update( @RequestBody FollowVo followVo,@PathVariable String id){
        followVo.setId(id);
        followService.update(followVo);
        return new Result(true,StatusCode.OK,"修改成功");
    }
    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping(value="/follow/{id}")
    @ApiOperation(value="删除")
    public Result deleteById(@PathVariable String id){
        followService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }
}
