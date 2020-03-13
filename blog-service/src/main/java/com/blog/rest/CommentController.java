package com.blog.rest;

import com.blog.controller.dto.article.CommentVo;
import com.blog.entity.Result;
import com.blog.entity.StatusCode;
import com.blog.service.CommentService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Api(value="文章评论", tags = "CommentController")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @PostMapping(value = "/comment")
    public Result save(@RequestBody CommentVo commentVo){
        commentService.add(commentVo);
        return new Result(true, StatusCode.OK, "评论成功");
    }

    /**
     * 查询评论
     * @param articleId
     * @return
     */
    @GetMapping(value="/comment/article/{articleId}")
    public Result findByArticleid(@PathVariable String articleId) throws Exception {
        return new Result(true, StatusCode.OK, "查询成功",commentService.findByArticleid(articleId));
    }

    /**
     * 删除评论
     * @param commentId
     * @return
     */
    @DeleteMapping(value="/comment/{commentId}")
    public Result deleteComment(@PathVariable String commentId){
        commentService.deleteComment(commentId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

}
