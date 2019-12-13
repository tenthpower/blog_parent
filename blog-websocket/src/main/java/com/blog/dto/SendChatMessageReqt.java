package com.blog.dto;

import lombok.Data;

@Data
public class SendChatMessageReqt {

    private String sendSid;

    private String sendMessage;

    /**
     * 系统，用户
     */
    private String messageType;

    /**
     * 公聊，群聊，私聊
     */
    private String sendTargetType;

    private String toId;
}
