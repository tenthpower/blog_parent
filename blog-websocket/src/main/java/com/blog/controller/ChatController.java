package com.blog.controller;

import com.blog.consts.WebSocketConsts;
import com.blog.dto.GetInfoBySidResp;
import com.blog.dto.GetInfoByTelNoResp;
import com.blog.dto.SendChatMessageReqt;
import com.blog.entity.Result;
import com.blog.entity.StatusCode;
import com.blog.util.*;
import com.blog.util.vo.MessageToFileVo;
import com.blog.util.vo.WSMessageVo;
import com.blog.websocket.SocketSessionRegistry;
import com.blog.websocket.vo.UserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Api(value="聊天", tags = "ChatController")
public class ChatController {

    private final Logger log = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private WSMessageUtil WSMessageUtil;

    @Autowired
    private FileUtil fileUtil;

    /**
     * 在线总人数
     * @return
     */
    @GetMapping(value="/chat/onlineCount")
    @ApiOperation(value="在线总人数")
    public Result onlineCount() throws Exception {
        long count = SocketSessionRegistry.registryUserInfoMap.values()
                .stream().filter(userInfo -> userInfo.getIsOnline() == WebSocketConsts.STATUS_ONLINE).count();
        log.info("当前在线总人数：{}", count);
        return new Result(true, StatusCode.OK, "查询成功", count);
    }

    /**
     * 发送消息
     * @return
     */
    @PostMapping(value="/chat/sendMessage")
    @ApiOperation(value="发送消息")
    public Result sendChatMessage(SendChatMessageReqt params) throws Exception {
        log.info("当前发送消息内容=[{}]", params);

        String sendDate = DateUtil.toString(DateUtil.getCurDate(),DateUtil.DATE_PATTERN_YYYYMMDDHHmmSS);
        String sendName = "";
        if (SocketSessionRegistry.registryUserInfoMap.get(params.getSendSid()) != null) {
            sendName = SocketSessionRegistry.registryUserInfoMap.get(params.getSendSid()).getName();
        }

        // 记录消息内容
        String fileName = params.getToId();
        MessageToFileVo messageToFileVo = new MessageToFileVo();
        messageToFileVo.setSendDate(sendDate);
        messageToFileVo.setSendMessage(params.getSendMessage());
        messageToFileVo.setSendName(sendName);
        messageToFileVo.setSendSid(params.getSendSid());
        fileUtil.saveChatMessage(fileName, messageToFileVo);

        // 发送消息
        WSMessageVo wsMessageVo = new WSMessageVo();
        wsMessageVo.setSendUserName(sendName);
        wsMessageVo.setSendSid(params.getSendSid());
        wsMessageVo.setSendMessage(params.getSendMessage());
        wsMessageVo.setMessageType(WebSocketConsts.TYPE_USER);
        wsMessageVo.setSendDate(sendDate);
        WSMessageUtil.sendMessage(wsMessageVo);
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
        List<UserInfo> userInfoMap = new ArrayList<>();
        SocketSessionRegistry.registryUserInfoMap.values()
                .stream()
                .filter(userInfo -> userInfo.getIsOnline() == WebSocketConsts.STATUS_ONLINE)
                .forEach(userInfo -> userInfoMap.add(userInfo));
        return new Result(true, StatusCode.OK, "查询成功", userInfoMap);
    }


    @GetMapping(value="/chat/getInfoBySid/{sid}")
    @ApiOperation(value="通过sid 获取信息")
    public Result getInfoBySid(@ApiParam(value="sid", required = true) @PathVariable String sid) throws Exception {
        UserInfo userInfo = SocketSessionRegistry.registryUserInfoMap.get(sid);
        GetInfoBySidResp getInfoBySidResp = new GetInfoBySidResp();
        if (userInfo != null) {
            getInfoBySidResp.setName(userInfo.getName());
            getInfoBySidResp.setSid(userInfo.getSid());
            getInfoBySidResp.setTelNo(userInfo.getTelNo());
            getInfoBySidResp.setIsHiddenTelNo(userInfo.getIsHiddenTelNo());
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
        Optional<UserInfo> optional = SocketSessionRegistry.registryUserInfoMap
                .values().stream().filter(x -> x.getTelNo().equals(telNo)).findFirst();
        GetInfoByTelNoResp  getInfoByTelNoResp = new GetInfoByTelNoResp();
        if (optional != null && optional.isPresent()) {
            getInfoByTelNoResp.setIsLogin(true);
            UserInfo userInfo = optional.get();
            getInfoByTelNoResp.setName(userInfo.getName());
            getInfoByTelNoResp.setSid(userInfo.getSid());
            getInfoByTelNoResp.setTelNo(userInfo.getTelNo());
            getInfoByTelNoResp.setIsHiddenTelNo(userInfo.getIsHiddenTelNo());
        } else {
            getInfoByTelNoResp.setIsLogin(false);
            getInfoByTelNoResp.setSid(ShaEncodeUtil.shaEncode(telNo));
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
