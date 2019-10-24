package com.blog.service.common;

import com.blog.dto.core.common.CreateApiByTableReqt;
import com.blog.dto.core.common.CreateApiByTableResp;
import com.blog.util.DemoVmHelper;
import com.blog.vo.TableInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 通过传入表名生成Api 接口文件的Service
 */
@Service("CreateApiByTableService")
@Transactional
public class CreateApiByTableService {

    private final static Logger log = LoggerFactory.getLogger(CreateApiByTableService.class);

    @Autowired
    @Qualifier("CreateApiValidator")
    private CreateApiValidator createApiValidator;

    @Autowired
    @Qualifier("QueryTableInfoService")
    private QueryTableInfoService queryTableInfoService;

    @Autowired
    private DemoVmHelper demoVmHelper;

    /**
     * 1. 请求参数数据校验
     * @param tableCreateApiReqt
     * @return
     * @throws Exception
     */
    public CreateApiByTableResp execute(CreateApiByTableReqt tableCreateApiReqt) throws Exception {
        // 定义返回值对象
        CreateApiByTableResp result = new CreateApiByTableResp();

        // 1. 请求参数数据校验
        this.createApiValidator.executeValidator(tableCreateApiReqt);

        // 2. 通过表名获取表结构
        List<TableInfoVo> tableFields = this.queryTableInfoService.execute(tableCreateApiReqt.getTableName());

        // 3. 通过表结构生成对应的java 结构数据
        // TODO

        // 4. vm 文件替换
        // 测试
        String demoVmResult = demoVmHelper.execute(tableCreateApiReqt.getTableName());
        log.info("demoVmHelper result=[{}]", demoVmResult);

        return result;
    }


}
