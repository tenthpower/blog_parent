package com.tenthpower.dao;

import com.tenthpower.pojo.Column;
import com.tenthpower.pojo.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
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
