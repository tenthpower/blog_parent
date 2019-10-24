package com.blog.rest.base;

import com.blog.dto.core.common.CreateApiByTableReqt;
import com.blog.dto.core.common.CreateApiByTableResp;
import com.blog.entity.Result;
import com.blog.entity.StatusCode;
import com.blog.service.common.CreateApiByTableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Api(value="对象公共Rest接口", tags = "CommonObjectRest")
public class CommonObjectRest {


    @Autowired
    @Qualifier("CreateApiByTableService")
    private CreateApiByTableService createApiByTableService;

    @PostMapping(value="/common/createApi")
    @ApiOperation(value="通过表创建api对象")
    public Result add(@RequestBody CreateApiByTableReqt tableCreateApiReqt) throws Exception {

        CreateApiByTableResp result = createApiByTableService.execute(tableCreateApiReqt);

        return null;
    }

}
