package com.blog.dto;

import lombok.Data;

@Data
public class SendChatMessageReqt {

    // 消息来源Id
    private String sendSid;

    // 消息内容
    private String sendMessage;

    /**
     * 消息类型：公聊，群聊，私聊
     */
    private String messageType;

    // 消息目标Id
    private String toId;

}
