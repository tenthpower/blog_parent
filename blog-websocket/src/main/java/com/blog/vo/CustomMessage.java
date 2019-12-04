package com.blog.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CustomMessage implements Serializable {

    private String sid;

    private String name;

    private String message;
}
