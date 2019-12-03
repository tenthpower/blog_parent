package com.blog.dao;

import com.blog.pojo.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * JpaRepository提供了基本的增删改查
 * JpaSpecificationExecutor用于做复杂的条件查询
 */
public interface EnterpriseDao extends JpaRepository<Enterprise, String>, JpaSpecificationExecutor<Enterprise> {

    /**
     * 根据热门状态获取企业信息列表
     * @param isHot
     * @return
     */
    public List<Enterprise> findByIsHot(Integer isHot);
}
