package com.blog.controller.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class GetInfoByTelNoResp implements Serializable {

    private static final long serialVersionUID = 1495675280489558317L;

    private Boolean isLogin;

    private String sid;

    private String name;

    private String telNo;

    private Integer isHiddenTelNo;

}
