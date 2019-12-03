package com.blog.rest;

import com.blog.dto.friend.FriendVo;
import com.blog.entity.Result;
import com.blog.entity.StatusCode;
import com.blog.service.FriendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Api(value="交友", tags = "FriendController")
public class FriendController {

    @Autowired
    private FriendService friendService;

    /**
     * 查询全部
     * @return
     */
    @GetMapping(value="/friend")
    @ApiOperation(value="查询全部")
    public Result findAll() throws Exception {
        return new Result(true, StatusCode.OK,"查询成功",friendService.findAll() );
    }
    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @GetMapping(value="/friend/{id}")
    @ApiOperation(value="根据ID查询")
    public Result findById(@PathVariable String id){
        return new Result(true,StatusCode.OK,"查询成功",friendService.findById(id));
    }
    /**
     * 增加
     * @param
     * @return
     */
    @PostMapping(value="/friend")
    @ApiOperation(value="增加")
    public Result add( @RequestBody FriendVo friendVo){
        friendService.add(friendVo);
        return new Result(true,StatusCode.OK,"增加成功");
    }
    /**
     * 修改
     * @param
     * @return
     */
    @PutMapping(value="/friend/{id}")
    @ApiOperation(value="修改")
    public Result update( @RequestBody FriendVo friendVo,@PathVariable String id){
        friendVo.setId(id);
        friendService.update(friendVo);
        return new Result(true,StatusCode.OK,"修改成功");
    }
    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping(value="/friend/{id}")
    @ApiOperation(value="删除")
    public Result deleteById(@PathVariable String id){
        friendService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }
}
