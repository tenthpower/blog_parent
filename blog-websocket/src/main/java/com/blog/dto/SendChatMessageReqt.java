package com.blog.dto;

import lombok.Data;

@Data
public class SendChatMessageReqt {

    private String sid;

    private String message;

    private String messageType;

    private String toId;
}
