package com.blog.service.common;

import com.blog.dto.core.common.CreateApiByTableReqt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 通过表名创建api文件的接口校验
 */
@Service("CreateApiValidator")
@Transactional
public class CreateApiValidator {

    /**
     * 生成api请求参数校验
     */
    public void executeValidator(CreateApiByTableReqt tableCreateApiReqt){

    }

}
