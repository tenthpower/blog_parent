package com.tenthpower.controller;

import com.tenthpower.dto.article.ColumnVo;
import com.tenthpower.dto.article.CommentVo;
import com.tenthpower.entity.Result;
import com.tenthpower.entity.StatusCode;
import com.tenthpower.service.ColumnService;
import com.tenthpower.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@Api(value="文章评论", tags = "CommentController")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @PostMapping(value = "")
    public Result save(@RequestBody CommentVo commentVo){
        commentService.add(commentVo);
        return new Result(true, StatusCode.OK, "评论成功");
    }

    /**
     * 查询评论
     * @param articleId
     * @return
     */
    @GetMapping(value="/article/{articleId}")
    public Result findByArticleid(@PathVariable String articleId) throws Exception {
        return new Result(true, StatusCode.OK, "查询成功",commentService.findByArticleid(articleId));
    }

    /**
     * 删除评论
     * @param commentId
     * @return
     */
    @DeleteMapping(value="/{commentId}")
    public Result deleteComment(@PathVariable String commentId){
        commentService.deleteComment(commentId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

}
