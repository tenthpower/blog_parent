package com.blog.rest;

import com.blog.config.DemoConfig;
import com.blog.entity.Result;
import com.blog.entity.StatusCode;
import com.blog.sender.DemoSender;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Api(value="demoController", tags = "DemoController")
public class DemoController {

    private Logger log = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    private DemoConfig demoConfig;

    @Autowired
    private DemoSender demoSender;

    /**
     * demo读取配置文件接口
     * @return
     */
    @GetMapping(value="/demo")
    @ApiOperation(value="登陆成功")
    public Result demo(){
        log.info("读取demo配置文件信息=[{}]", demoConfig);
        Result result = new Result(true,StatusCode.OK,"-");
        return result;
    }

    /**
     * 队列生产/消费的demo
     * @return
     */
    @GetMapping(value="/demoSender")
    @ApiOperation(value="登陆成功")
    public Result demoSender(){
        demoSender.demoSender("这是一个队列消息内容");
        Result result = new Result(true, StatusCode.OK,"-");
        return result;
    }

}
