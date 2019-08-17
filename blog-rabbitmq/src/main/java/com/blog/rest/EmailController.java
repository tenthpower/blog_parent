package com.blog.rest;

import com.blog.config.DemoConfig;
import com.blog.dto.rabbitmq.EmailVo;
import com.blog.entity.Result;
import com.blog.entity.StatusCode;
import com.blog.sender.SendEmailSender;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
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
    @PostMapping(value="/email/send")
    @ApiOperation(value="附件邮件发送")
    public Result send(@RequestBody EmailVo emailVo){
        sendEmailSender.sendEmailSender(emailVo);
        Result result = new Result(true, StatusCode.OK,"邮件发送成功！");
        return result;
    }

}
