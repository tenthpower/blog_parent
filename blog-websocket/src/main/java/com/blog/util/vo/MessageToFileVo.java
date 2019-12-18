package com.blog.util.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MessageToFileVo implements Serializable {

    private static final long serialVersionUID = -8820867230466089988L;

    private String sendSid;

    private String sendName;

    private String sendMessage;

    private String sendDate;

}
