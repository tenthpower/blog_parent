package com.blog.rest;

import com.blog.controller.dto.recruit.EnterpriseVo;
import com.blog.entity.Result;
import com.blog.entity.StatusCode;
import com.blog.service.EnterpriseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Api(value="企业", tags = "EnterpriseController")
public class EnterpriseController {

    @Autowired
    private EnterpriseService enterpriseService;

    /**
     * 查询全部
     * @return
     */
    @GetMapping(value="/enterprise")
    @ApiOperation(value="查询全部")
    public Result findAll() throws Exception {
        return new Result(true, StatusCode.OK,"查询成功",enterpriseService.findAll() );
    }
    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @GetMapping(value="/enterprise/{id}")
    @ApiOperation(value="根据ID查询")
    public Result findById(@PathVariable String id){
        return new Result(true,StatusCode.OK,"查询成功",enterpriseService.findById(id));
    }
    /**
     * 增加
     * @param
     * @return
     */
    @PostMapping(value="/enterprise")
    @ApiOperation(value="增加")
    public Result add( @RequestBody EnterpriseVo enterpriseVo){
        enterpriseService.add(enterpriseVo);
        return new Result(true,StatusCode.OK,"增加成功");
    }
    /**
     * 修改
     * @param
     * @return
     */
    @PutMapping(value="/enterprise/{id}")
    @ApiOperation(value="修改")
    public Result update( @RequestBody EnterpriseVo enterpriseVo,@PathVariable String id){
        enterpriseVo.setId(id);
        enterpriseService.update(enterpriseVo);
        return new Result(true,StatusCode.OK,"修改成功");
    }
    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping(value="/enterprise/{id}")
    @ApiOperation(value="删除")
    public Result deleteById(@PathVariable String id){
        enterpriseService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /**
     * 查询热门企业
     * @return
     */
    @RequestMapping(value="/enterprise/search/hotlist",method=RequestMethod.GET)
    public Result hotlist() throws Exception {
        return new Result(true, StatusCode.OK, "查询成功",enterpriseService.hotList());
    }
}
