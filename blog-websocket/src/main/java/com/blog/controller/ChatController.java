package com.blog.controller;

import com.blog.consts.WebSocketConsts;
import com.blog.entity.Result;
import com.blog.entity.StatusCode;
import com.blog.util.MessageSendUtil;
import com.blog.util.WebSocketInfoUtil;
import com.blog.vo.ChatInfoVo;
import com.blog.vo.ChatMessage;
import com.blog.vo.CustomMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api")
@Api(value="聊天", tags = "ChatController")
public class ChatController {

    private final Logger log = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private MessageSendUtil messageSendUtil;

    /**
     * 在线总人数
     * @return
     */
    @GetMapping(value="/chat/onlineCount")
    @ApiOperation(value="在线总人数")
    public Result onlineCount() throws Exception {
        Integer count = WebSocketInfoUtil.chatInfoMap.size();
        log.info("当前在线总人数：{}", count);
        return new Result(true, StatusCode.OK, "查询成功", count);
    }

    /**
     * 发送消息
     * @return
     */
    @GetMapping(value="/chat/sendMessage")
    @ApiOperation(value="发送消息")
    public Result sendChatMessage(CustomMessage customMessage) throws Exception {
        log.info("当前发送消息内容=[{}]", customMessage);
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessage(customMessage.getMessage());
        chatMessage.setSendTargetType(WebSocketConsts.SEND_MESSAGE_TYPE_PUBLIC);// TODO
        chatMessage.setMessageType(WebSocketConsts.MESSAGE_TYPE_USER);
        messageSendUtil.sendMessage(chatMessage);
        return new Result(true, StatusCode.OK, "消息发送成功！", null);
    }

    /**
     * 在线人数列表
     * @return
     */
    @GetMapping(value="/chat/onlineList")
    @ApiOperation(value="在线人数列表")
    public Result chatList() throws Exception {
        ConcurrentHashMap<String, ChatInfoVo> chatInfoMap = WebSocketInfoUtil.chatInfoMap;
        return new Result(true, StatusCode.OK, "查询成功", chatInfoMap);
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
