package com.blog.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginInReqt implements Serializable {

    private String sid;

    private String name;

    private String telNo;

    private Integer isHiddenTelNo;

}
