package com.blog.rest;

import com.blog.entity.Result;
import com.blog.entity.StatusCode;
import com.blog.sender.SendSmsSender;
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
@Api(value="smsController", tags = "SmsController")
public class SmsController {

    private Logger log = LoggerFactory.getLogger(SmsController.class);

    @Autowired
    private SendSmsSender sendSmsSender;

    /**
     * 发送短信验证码
     * @param telNo
     * @return
     */
    @PostMapping(value="/sms/sendCode/{telNo}")
    @ApiOperation(value="发送短信验证码")
    public Result send(@RequestBody String telNo){
        String message = sendSmsSender.sendSmsSender(telNo);
        Result result = new Result(true, StatusCode.OK,message);
        return result;
    }

}
