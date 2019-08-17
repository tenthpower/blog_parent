package com.blog.dao;

import com.blog.pojo.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * 评论的MongoDB 的dao
 */
public interface CommentDao extends MongoRepository<Comment, String> {

    /**
     * 根据文章ID查询评论列表
     *  @param articleId
     * @return
     */
    public List<Comment> findByArticleId(String articleId);

}
