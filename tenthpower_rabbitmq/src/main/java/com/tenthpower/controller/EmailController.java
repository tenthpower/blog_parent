package com.tenthpower.controller;

import com.tenthpower.config.DemoConfig;
import com.tenthpower.dto.user.AdminVo;
import com.tenthpower.entity.Result;
import com.tenthpower.entity.StatusCode;
import com.tenthpower.rabbitmq.sender.DemoSender;
import com.tenthpower.rabbitmq.sender.SendEmailSender;
import com.tenthpower.vo.EmailVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
@Api(value="emailController", tags = "EmailController")
public class EmailController {

    private Logger log = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    private DemoConfig demoConfig;

    @Autowired
    private SendEmailSender sendEmailSender;

    /**
     * 无附件邮件发送
     * @param emailVo
     * @return
     */
    @PostMapping(value="/send")
    @ApiOperation(value="附件邮件发送")
    public Result send(@RequestBody EmailVo emailVo){
        sendEmailSender.sendEmailSender(emailVo);
        Result result = new Result(true,StatusCode.OK,"邮件发送成功！");
        return result;
    }

}
