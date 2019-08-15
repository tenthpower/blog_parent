package com.tenthpower.controller;

import com.tenthpower.config.DemoConfig;
import com.tenthpower.dto.user.AdminVo;
import com.tenthpower.entity.Result;
import com.tenthpower.entity.StatusCode;
import com.tenthpower.rabbitmq.sender.DemoSender;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
@Api(value="demoController", tags = "DemoController")
public class DemoController {

    private Logger log = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    private DemoConfig demoConfig;

    @Autowired
    private DemoSender demoSender;

    /**
     * demo读取配置文件接口
     * @param adminVo
     * @return
     */
    @GetMapping(value="/api/demo")
    @ApiOperation(value="登陆成功")
    public Result demo(@RequestBody AdminVo adminVo){
        log.info("读取demo配置文件信息=[{}]", demoConfig);
        Result result = new Result(true,StatusCode.OK,"-");
        return result;
    }

    /**
     * 队列生产/消费的demo
     * @return
     */
    @GetMapping(value="/api/demoSender")
    @ApiOperation(value="登陆成功")
    public Result demoSender(){
        demoSender.demoSender("这是一个队列消息内容");
        Result result = new Result(true,StatusCode.OK,"-");
        return result;
    }

}
