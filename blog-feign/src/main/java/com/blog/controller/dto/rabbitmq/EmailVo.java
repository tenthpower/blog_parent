package com.blog.controller.dto.rabbitmq;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EmailVo implements Serializable {

    private static final long serialVersionUID = -9042185210767895361L;

    /**
     * 邮箱标题
     */
    private String title;

    /**
     * 邮箱正文
     */
    private String content;

    /**
     * 邮件类型：html or text
     */
    private String sign;

    /**
     * 抄送人集合
     */
    private List<ReceiverPeoperVo> ccPeopleVos = null;

    /**
     * 收件人集合
     */
    private List<ReceiverPeoperVo> receiverPeopleVos = null;
}
