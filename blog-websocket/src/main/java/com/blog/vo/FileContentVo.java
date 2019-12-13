package com.blog.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class FileContentVo implements Serializable {

    private static final long serialVersionUID = -8820867608998230468L;

    private Date time;

    private String message;

}
