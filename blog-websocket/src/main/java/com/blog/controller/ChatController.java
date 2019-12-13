package com.blog.controller;

import com.blog.consts.WebSocketConsts;
import com.blog.dto.GetInfoBySidResp;
import com.blog.dto.GetInfoByTelNoResp;
import com.blog.dto.SendChatMessageReqt;
import com.blog.entity.Result;
import com.blog.entity.StatusCode;
import com.blog.util.*;
import com.blog.vo.ChatInfoVo;
import com.blog.vo.ChatMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api")
@Api(value="聊天", tags = "ChatController")
public class ChatController {

    private final Logger log = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private MessageSendUtil messageSendUtil;

    @Autowired
    private FileUtil fileUtil;

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
    @PostMapping(value="/chat/sendMessage")
    @ApiOperation(value="发送消息")
    public Result sendChatMessage(SendChatMessageReqt sendChatMessageReqt) throws Exception {
        log.info("当前发送消息内容=[{}]", sendChatMessageReqt);

        // 记录消息内容
        fileUtil.saveChatMessage(sendChatMessageReqt);

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSendMessage(sendChatMessageReqt.getSendMessage());
        chatMessage.setSendSid(sendChatMessageReqt.getSendSid());
        if (WebSocketInfoUtil.chatInfoMap.get(sendChatMessageReqt.getSendSid()) != null) {
            chatMessage.setSendUserName(WebSocketInfoUtil.chatInfoMap.get(sendChatMessageReqt.getSendSid()).getName());
        }
        chatMessage.setMessageType(sendChatMessageReqt.getMessageType());
        chatMessage.setSendTargetType(sendChatMessageReqt.getSendTargetType());
        chatMessage.setToId(sendChatMessageReqt.getToId());
        chatMessage.setSendDate(DateUtil.toString(DateUtil.getCurDate(),DateUtil.DATE_PATTERN_YYYYMMDDHHmmSS));
        messageSendUtil.sendMessage(chatMessage);
        return new Result(true, StatusCode.OK, "消息发送成功！", null);
    }

    /**
     * 在线人数列表
     * @return
     * @throws Exception
     */
    @GetMapping(value="/chat/onlineList")
    @ApiOperation(value="在线人数列表")
    public Result chatList() throws Exception {
        ConcurrentHashMap<String, ChatInfoVo> chatInfoMap = WebSocketInfoUtil.chatInfoMap;
        return new Result(true, StatusCode.OK, "查询成功", chatInfoMap);
    }


    @GetMapping(value="/chat/getInfoBySid/{sid}")
    @ApiOperation(value="通过sid 获取信息")
    public Result getInfoBySid(@ApiParam(value="sid", required = true) @PathVariable String sid) throws Exception {
        ChatInfoVo chatInfoMap = WebSocketInfoUtil.chatInfoMap.get(sid);
        GetInfoBySidResp getInfoBySidResp = new GetInfoBySidResp();
        if (chatInfoMap != null) {
            getInfoBySidResp.setName(chatInfoMap.getName());
            getInfoBySidResp.setSid(chatInfoMap.getSid());
            getInfoBySidResp.setTelNo(chatInfoMap.getTelNo());
        }
        return new Result(true, StatusCode.OK, "查询成功", getInfoBySidResp);
    }

    /**
     * 通过手机号 获取信息，看有没有登陆信息
     * @return
     */
    @GetMapping(value="/chat/getInfoByTelNo/{telNo}")
    @ApiOperation(value="通过手机号 获取信息，看有没有登陆信息")
    public Result getInfoByTelNo(@ApiParam(value="手机号", required = true) @PathVariable String telNo) throws Exception {
        ConcurrentHashMap<String, ChatInfoVo> chatInfoMap = WebSocketInfoUtil.chatInfoMap;
        Optional<ChatInfoVo> optional =
                chatInfoMap.values().stream().filter(x -> x.getTelNo().equals(telNo)).findFirst();
        GetInfoByTelNoResp  getInfoByTelNoResp = new GetInfoByTelNoResp();
        if (optional != null && optional.isPresent()) {
            getInfoByTelNoResp.setIsLogin(true);
            getInfoByTelNoResp.setName(optional.get().getName());
            getInfoByTelNoResp.setSid(optional.get().getSid());
            getInfoByTelNoResp.setTelNo(optional.get().getTelNo());
        } else {
            getInfoByTelNoResp.setIsLogin(false);
            getInfoByTelNoResp.setSid(ChatInfoUtil.shaEncode(telNo));
        }
        return new Result(true, StatusCode.OK, "查询成功", getInfoByTelNoResp);
    }

    /**
     * TODO 聊天记录回显
     */
    @PostMapping(value="/chat/history")
    @ApiOperation(value="聊天记录回显")
    public Result chatHistory() throws Exception {
        return new Result();
    }

}
