package com.blog.dto.rabbitmq;

import lombok.Data;

import java.io.Serializable;

@Data
public class DemoVo implements Serializable {

    private static final long serialVersionUID = -7687459844676037462L;

    private String name;

    private Integer age;

    private String message;

}
