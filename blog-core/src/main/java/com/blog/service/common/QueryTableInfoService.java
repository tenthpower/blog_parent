package com.blog.service.common;

import com.blog.vo.TableInfoVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 通过表名查询 表结构信息
 */
@Service("QueryTableInfoService")
@Transactional
public class QueryTableInfoService {

    /**
     * 通过sql: show full fields from demo
     * @param tableName
     * @return
     * @throws Exception
     */
    public List<TableInfoVo> execute(String tableName) throws Exception {
        // TODO

        return null;
    }

}
