package com.blog.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class GetInfoBySidResp implements Serializable {

    private static final long serialVersionUID = 1495675280489558317L;

    private String sid;

    private String name;

    private String telNo;

}
