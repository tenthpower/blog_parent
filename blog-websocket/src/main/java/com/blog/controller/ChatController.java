package com.blog.controller;

import com.blog.entity.Result;
import com.blog.entity.StatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Api(value="聊天", tags = "ChatController")
public class ChatController {


    /**
     * 在线总人数
     * @return
     */
    @GetMapping(value="/chat/onlineCount")
    @ApiOperation(value="在线总人数")
    public Result onlineCount() throws Exception {
        return new Result();
    }

    /**
     * 发送消息
     * @return
     */
    @GetMapping(value="/chat/sendMessage")
    @ApiOperation(value="发送消息")
    public Result sendChatMessage() throws Exception {
        return new Result();
    }

    /**
     * 在线人数列表
     * @return
     */
    @GetMapping(value="/chat/onlineList")
    @ApiOperation(value="在线人数列表")
    public Result chatList() throws Exception {
        return new Result();
    }

    /**
     * 聊天记录回显
     */
    @GetMapping(value="/chat/history")
    @ApiOperation(value="聊天记录回显")
    public Result chatHistory() throws Exception {
        return new Result();
    }

}
