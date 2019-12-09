package com.blog.util;

import com.blog.consts.WebSocketConsts;
import com.blog.vo.ChatMessage;
import com.blog.vo.OnLineInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * 消息广播工具类
 */
@Component
public class MessageSendUtil {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    public void sendMessage(ChatMessage chatMessage){
        chatMessage.setSendTargetType(WebSocketConsts.SEND_MESSAGE_TYPE_PUBLIC);// TODO

        if (chatMessage == null || StringUtils.isBlank(chatMessage.getMessageType())) {
            return;
        }
        if (StringUtils.equals(chatMessage.getSendTargetType(), WebSocketConsts.SEND_MESSAGE_TYPE_PUBLIC)) {
            messagingTemplate.convertAndSend("/topic/notice", chatMessage);
        } else if (StringUtils.equals(chatMessage.getSendTargetType(), WebSocketConsts.SEND_MESSAGE_TYPE_GROUP)) {
            // TODO 给组中的人发送消息
        } else if (StringUtils.equals(chatMessage.getSendTargetType(), WebSocketConsts.SEND_MESSAGE_TYPE_PRIVATELY)) {
            // TODO 私聊的人发送消息
        }
    }

    public void sendOnlineInfo(){
        OnLineInfoVo onLineInfoVo = new OnLineInfoVo();
        onLineInfoVo.setChatCount(WebSocketInfoUtil.chatInfoMap.size());
        onLineInfoVo.setGroupCount(WebSocketInfoUtil.groupChatInfoMap.size());
        onLineInfoVo.setChatInfoList(WebSocketInfoUtil.chatInfoMap.values().stream().collect(Collectors.toList()));
        messagingTemplate.convertAndSend("/topic/online", onLineInfoVo);
    }

}
