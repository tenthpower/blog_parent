package com.blog.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SaveChatMessageVo implements Serializable {

    private static final long serialVersionUID = -8820867230466089988L;

    private String sendSid;

    private String sendName;

    private String sendMessage;

    private Date sendDate;

}
