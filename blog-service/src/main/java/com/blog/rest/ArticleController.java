package com.blog.rest;

import com.blog.dto.article.ArticleVo;
import com.blog.entity.Result;
import com.blog.entity.StatusCode;
import com.blog.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Api(value="文章", tags = "ArticleController")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 查询全部
     * @return
     */
    @GetMapping(value="/article")
    @ApiOperation(value="查询全部")
    public Result findAll() throws Exception {
        return new Result(true, StatusCode.OK,"查询成功",articleService.findAll() );
    }
    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @GetMapping(value="/article/{id}")
    @ApiOperation(value="根据ID查询")
    public Result findById(@PathVariable String id){
        return new Result(true,StatusCode.OK,"查询成功",articleService.findById(id));
    }
    /**
     * 增加
     * @param
     * @return
     */
    @PostMapping(value="/article")
    @ApiOperation(value="增加")
    public Result add( @RequestBody ArticleVo articleVo){
        articleService.add(articleVo);
        return new Result(true,StatusCode.OK,"增加成功");
    }
    /**
     * 修改
     * @param
     * @return
     */
    @PutMapping(value="/article/{id}")
    @ApiOperation(value="修改")
    public Result update( @RequestBody ArticleVo articleVo,@PathVariable String id){
        articleVo.setId(id);
        articleService.update(articleVo);
        return new Result(true,StatusCode.OK,"修改成功");
    }
    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping(value="/article/{id}")
    @ApiOperation(value="删除")
    public Result deleteById(@PathVariable String id){
        articleService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /**
     * 文章审核
     * @param id
     * @return
     */
    @PutMapping(value="/article/examine/{id}")
    @ApiOperation(value="文章审核")
    public Result examine(@PathVariable String id){
        articleService.examine(id);
        return new Result(true, StatusCode.OK, "审核成功！");
    }

    /**
     * 文章点赞
     * @param id
     * @return
     */
    @PutMapping(value="/article/thumbup/{id}")
    @ApiOperation(value="文章点赞")
    public Result updateThumbup(@PathVariable String id){
        articleService.updateThumbup(id);
        return new Result(true, StatusCode.OK,"点赞成功");
    }

}
