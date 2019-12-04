package com.blog.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CustomMessage implements Serializable {

    private String id;

    private String name;

    private String responseMessage;
}
