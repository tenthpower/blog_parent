package com.blog.dao;

import com.blog.pojo.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * JpaRepository提供了基本的增删改查
 * JpaSpecificationExecutor用于做复杂的条件查询
 */
public interface ProblemDao extends JpaRepository<Problem, String>, JpaSpecificationExecutor<Problem> {

    /**
     * 根据标签ID查询最新问题列表
     * @param labelId
     * @param pageable
     * @return
     */
    @Query("select p from Problem p where id in(select problemId from Pl where labelId= :labelId ) order by " +
            "replyTime desc")
    public Page<Problem> findNewListByLabelId(@Param("labelId") String labelId, Pageable pageable);

    /**
     * 根据标签ID查询热门问题列表:热门问答列表
     * @param labelId
     * @param pageable
     * @return
     */
    @Query("select p from Problem p where id in( select problemId from Pl where labelId= :labelId) order by " +
            "reply desc")
    public Page<Problem> findHotListByLabelId(@Param("labelId") String labelId, Pageable pageable);

    /**
     * 根据标签ID查询等待回答列表
     * @param labelId
     * @param pageable
     * @return
     */
    @Query("select p from Problem p where id in(select problemId from Pl where labelId= :labelId) and reply=0 " +
            "order by createTime desc")
    public Page<Problem> findWaitListByLabelId(@Param("labelId")String labelId, Pageable pageable);

}
