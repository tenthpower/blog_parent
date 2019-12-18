package com.blog.util.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class WSMessageVo implements Serializable {

    private String sendSid;

    private String sendUserName;

    private String sendMessage;

    private String messageType;

    private String sendDate;

}
