package com.blog.rest;

import com.blog.dto.recruit.RecruitVo;
import com.blog.entity.Result;
import com.blog.entity.StatusCode;
import com.blog.service.RecruitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Api(value="职位", tags = "RecruitController")
public class RecruitController {

    @Autowired
    private RecruitService recruitService;

    /**
     * 查询全部
     * @return
     */
    @GetMapping(value="/recruit")
    @ApiOperation(value="查询全部")
    public Result findAll() throws Exception {
        return new Result(true, StatusCode.OK,"查询成功",recruitService.findAll() );
    }
    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @GetMapping(value="/recruit/{id}")
    @ApiOperation(value="根据ID查询")
    public Result findById(@PathVariable String id){
        return new Result(true,StatusCode.OK,"查询成功",recruitService.findById(id));
    }
    /**
     * 增加
     * @param
     * @return
     */
    @PostMapping(value="/recruit")
    @ApiOperation(value="增加")
    public Result add( @RequestBody RecruitVo recruitVo){
        recruitService.add(recruitVo);
        return new Result(true,StatusCode.OK,"增加成功");
    }
    /**
     * 修改
     * @param
     * @return
     */
    @PutMapping(value="/recruit/{id}")
    @ApiOperation(value="修改")
    public Result update( @RequestBody RecruitVo recruitVo,@PathVariable String id){
        recruitVo.setId(id);
        recruitService.update(recruitVo);
        return new Result(true,StatusCode.OK,"修改成功");
    }
    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping(value="/recruit/{id}")
    @ApiOperation(value="删除")
    public Result deleteById(@PathVariable String id){
        recruitService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /**
     * 推荐职位列表
     * @param recruitVo
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/recruit/search/recommend",method= RequestMethod.GET)
    public Result recommend(@RequestBody RecruitVo recruitVo) throws Exception {
        List<RecruitVo> list = recruitService.findTop4ByStateOrderByCreateTimeDesc(recruitVo);
        return new Result(true,StatusCode.OK,"查询成功",list);
    }

    /**
     * 最新职位列表
     * @return
     */
    @RequestMapping(value="/recruit/search/newList",method= RequestMethod.GET)
    public Result newlist() throws Exception {
        return new Result(true,StatusCode.OK,"查询成功",recruitService.newList());
    }

}
