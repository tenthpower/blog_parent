package com.blog.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChatMessage implements Serializable {

    private String sendSid;

    private String sendUserName;

    private String message;

    private String messageType;

    private String sendTargetType;

    /**
     * 到达Id：目标sid或者组Id
     */
    private String toId;

}