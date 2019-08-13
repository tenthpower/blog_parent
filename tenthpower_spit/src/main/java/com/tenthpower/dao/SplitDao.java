package com.tenthpower.dao;

import com.tenthpower.pojo.Split;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SplitDao extends MongoRepository<Split,String> {
    /**
     * 根据上级ID查询吐槽列表
     * @param parentid
     * @param pageable
     * @return
     */
    public Page<Split> findByParentid(String parentid,Pageable pageable);
}
