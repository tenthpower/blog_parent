package com.blog.dto.rabbitmq;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReceiverPeoperVo implements Serializable {

    /**
     * 收件人账号
     */
    private String receiverAccount;

    /**
     * 收件人昵称
     */
    private String receiverNickName;
}
